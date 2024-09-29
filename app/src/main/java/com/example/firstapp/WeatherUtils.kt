import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.firstapp.R
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun getBackgroundImage(weatherData: Weather?): Int {
    val localtime = weatherData?.location?.localtime

    return if (localtime != null) {
        // Parse the time from the API response
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dateTime = LocalDateTime.parse(localtime, formatter)
        val hour = dateTime.hour

        when {
            hour >= 20 || hour < 6 -> R.drawable.background // Night from 8 PM to 6:59 AM
            hour in 6..16 -> R.drawable.sun   // Day from 7 AM to 4:59 PM
            hour in 17..19 -> R.drawable.moon  // Noon from 5 PM to 7:59 PM
            else -> R.drawable.background        // Default background
        }
    } else {
        R.drawable.background // Default background if localtime is null
    }
}

//
val quickFontFamily = FontFamily(
    Font(R.font.quicksand_variablefont_wght), // Use the appropriate resource name
    // Add bold or other variants if available
)

val ralewayFontFamily = FontFamily(

    Font(R.font.raleway_variablefont_wght)
)

@RequiresApi(Build.VERSION_CODES.O)
fun getDay(weatherData: Weather?): DayOfWeek {

    val localtime = weatherData?.location?.localtime
    if (localtime != null) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dateTime = LocalDateTime.parse(localtime, formatter)
        val day = dateTime.dayOfWeek

        return day
    } else {
        return DayOfWeek.MONDAY
    }
}


