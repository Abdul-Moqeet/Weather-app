package com.example.firstapp.Compose_Ui

import WeatherViewModel
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firstapp.R
import com.example.firstapp.models.famousCities
import getBackgroundImage
import getDay
import kotlinx.coroutines.launch
import quickFontFamily
import ralewayFontFamily


@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
@OptIn(ExperimentalMaterial3Api::class)

fun WeatherScreen() {


    val viewModel: WeatherViewModel = viewModel()
    val weatherData by viewModel.weatherData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var selectedCity by remember { mutableStateOf("london") }


    fun toggleBottomSheet() {
        scope.launch {
            if (sheetState.isVisible) {
                sheetState.hide()
                showBottomSheet = false
            } else {
                sheetState.show()
                showBottomSheet = true
            }
        }
    }




    LaunchedEffect(Unit) {
        viewModel.fetchWeather(city = selectedCity)
        val temperatureIcon = R.drawable.temperature
        val humidityIcon = R.drawable.humidity
        val windIcon = R.drawable.wind


        Log.d("ResourceCheck", "Temperature Icon ID: $temperatureIcon")
        Log.d("ResourceCheck", "Humidity Icon ID: $humidityIcon")
        Log.d("ResourceCheck", "Wind Icon ID: $windIcon")

    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = getBackgroundImage(weatherData = weatherData)),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Scaffold(

            containerColor = Color.Transparent,

            content = { paddingValues ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {

                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 20.dp, top = 20.dp)
                            .size(30.dp)
                            .clickable { toggleBottomSheet() },
                    )
                    if (isLoading == false) {
                        Text(text = weatherData?.location?.name ?: "Unknown Location",
                            color = Color.White,
                            fontSize = 40.sp,
                            fontFamily = FontFamily.SansSerif,
                            modifier = Modifier
                                .padding(top = 50.dp)
                                .clickable {
                                    viewModel.fetchWeather(selectedCity)  // Fetch weather on click
                                })

                        // Display temperature
                        Text(text = "${weatherData?.current?.tempC?.toInt() ?: 0}°C",  // Default to 0 if tempC is null
                            color = Color.White,
                            fontSize = 60.sp,
                            fontFamily = ralewayFontFamily,
                            modifier = Modifier
                                .clickable {
                                    viewModel.fetchWeather(selectedCity)  // Fetch weather on click
                                }
                                .padding(top = 20.dp))

                        // Display condition text
                        Text(
                            text = weatherData?.current?.condition?.text
                                ?: "Condition Unknown",  // Default message if condition is null
                            color = Color.White,
                            fontSize = 30.sp,
                            fontFamily = quickFontFamily,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    } else {
                        Text(
                            text = "No Data",
                            color = Color.White,
                            fontSize = 40.sp,
                            fontFamily = FontFamily.SansSerif,
                            modifier = Modifier.padding(top = 100.dp)
                        )
                    }
//                    Spacer(modifier = Modifier.fillMaxSize())
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp)
                                .align(Alignment.BottomCenter)
                        ) {
                            Text(
                                text = getDay(weatherData = weatherData).toString(),
                                color = Color.White,
                                fontSize = 30.sp,
                                modifier = Modifier.padding(start = 20.dp),
                                fontFamily = quickFontFamily,
                                fontWeight = FontWeight(weight = 900)
                            )
                            HorizontalDivider(
                                modifier = Modifier.padding(
                                    vertical = 8.dp, horizontal = 20.dp
                                ), // Set the height of the vertical divider
                                thickness = 5.dp, color = Color.LightGray
                            )
                            Spacer(modifier = Modifier.height(height = 10.dp))
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.weight(1f) // Distribute space equally
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.temperature),
                                        contentDescription = "Temperature",
                                        modifier = Modifier.size(35.dp), // Adjust the size as needed
                                        tint = Color.White
                                    )
                                    Vertical_lines()
                                    Text(
                                        text = "${weatherData?.current?.feelslikeC?.toInt()}°C",
                                        color = Color.White
                                    )
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.wind),
                                        contentDescription = "Wind",
                                        modifier = Modifier.size(35.dp), // Adjust the size as needed
                                        tint = Color.White
                                    )
                                    Vertical_lines()
                                    Text(
                                        text = "${weatherData?.current?.windKph?.toInt()} Km/h",
                                        color = Color.White
                                    )
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.humidity),
                                        contentDescription = "Humidity",
                                        modifier = Modifier.size(35.dp), // Adjust the size as needed
                                        tint = Color.White
                                    )
                                    Vertical_lines()
                                    Text(
                                        text = "${weatherData?.current?.humidity?.toInt()}",
                                        color = Color.White
                                    )
                                }
                            }

                        }
                        if (showBottomSheet) {
                            ModalBottomSheet(
                                onDismissRequest = {
                                    showBottomSheet = false
                                },
                                sheetState = sheetState
                            ) {
                                LazyColumn(modifier = Modifier.fillMaxSize()) {
                                    items(famousCities.size) { index: Int ->
                                        Text(
                                            text = famousCities[index].replace(oldChar ='_', newChar = ' '),
                                            fontSize = 20.sp,
                                            modifier = Modifier.padding(16.dp).clickable {
                                                selectedCity = famousCities[index]
                                                viewModel.fetchWeather(selectedCity)
                                            }
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                    }
                                }
                            }
                        }



                    }

                    // Define your VerticalDivider composable


                }
            })
    }
}

//
@Composable
fun Vertical_lines() {
    VerticalDivider(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .height(20.dp), // Set the height of the vertical divider
        thickness = 5.dp
    )
}
//

