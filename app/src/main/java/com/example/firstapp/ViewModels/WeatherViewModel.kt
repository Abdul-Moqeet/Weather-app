import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val apiClient = WeatherApiClient()
    private val _weatherData = MutableStateFlow<Weather?>(null)
    val weatherData: StateFlow<Weather?> = _weatherData

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchWeather(city: String) {
        var apiUrl = getApiUrl(city)
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiClient.fetchWeather(apiUrl)
                response?.let {
                    _weatherData.value = Gson().fromJson(it, Weather::class.java)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}