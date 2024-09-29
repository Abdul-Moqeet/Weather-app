import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class WeatherApiClient {

    private val client = OkHttpClient()

    suspend fun fetchWeather(apiUrl: String): String? {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder().url(apiUrl).build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                response.body?.string()
            }
        }
    }
}