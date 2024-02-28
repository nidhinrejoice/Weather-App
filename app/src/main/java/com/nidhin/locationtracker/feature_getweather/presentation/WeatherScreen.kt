package com.nidhin.locationtracker.feature_getweather.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nidhin.locationtracker.feature_getweather.domain.models.LocationWeatherData

@Composable
fun WeatherScreen(
    weatherData: LocationWeatherData,
    viewModel: WeatherDataViewModel = hiltViewModel()
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(viewModel.isRefreshing.value),
        onRefresh = {
            viewModel.getLatLngWeather(
                viewModel.state.value.lat,
                viewModel.state.value.lng
            )
        },
    ) {

//        val searchText = remember { mutableStateOf("") }
//        val suggestions = remember { mutableStateOf(listOf<String>()) }
//        val allCities = listOf("New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose")
//
//        // Update suggestions based on the search text
//        suggestions.value = if (searchText.value.isEmpty()) {
//            emptyList()
//        } else {
//            allCities.filter { it.contains(searchText.value, ignoreCase = true) }
//        }
//        Column {
//            OutlinedTextField(
//                value = searchText.value,
//                onValueChange = { searchText.value = it },
//                label = { Text("Enter city name") },
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//            AnimatedVisibility(visible = suggestions.value.isNotEmpty(),
//                enter = fadeIn() + expandHorizontally(), exit = fadeOut() + shrinkHorizontally()
//            ) {
//                LazyColumn {
//                    items(suggestions.value){
//                        Text(text = it)
//                    }
//                }
//            }
//        }
        var weatherSource: WeatherDataViewModel.WeatherApi by remember {
            mutableStateOf(
                WeatherDataViewModel.WeatherApi.OPEN_WEATHER_API
            )
        }
        Column {
            weatherData.let {
                ElevatedCard(elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            IconButton(
                                onClick = {
                                    viewModel.getLatLngWeather(
                                        viewModel.state.value.lat,
                                        viewModel.state.value.lng,
                                        weatherSource
                                    )
                                },
                                modifier = Modifier.padding(8.dp) // Adjust padding as needed
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Refresh",
                                    tint = Color.Black // Adjust tint color as needed
                                )
                            }
                        }
                        Text(
                            "${it.main.temp.toInt()}°C",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "feels like ${it.main.feels_like.toInt()}°C",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = it.weather.firstOrNull()?.description?.uppercase() ?: "N/A",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Humidity: ${it.main.humidity}%",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = "Wind Speed: ${it.wind.speed}m/s",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

            Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                Row (verticalAlignment = Alignment.CenterVertically){

                    RadioButton(
                        selected = weatherSource == WeatherDataViewModel.WeatherApi.OPEN_WEATHER_API,
                        onClick = {
                            weatherSource = WeatherDataViewModel.WeatherApi.OPEN_WEATHER_API
                            viewModel.getLatLngWeather(
                                viewModel.state.value.lat,
                                viewModel.state.value.lng,
                                weatherSource
                            )
                        },
                        modifier = Modifier.padding(2.dp)
                    )
                    Text(text = "Open Weather API", style = MaterialTheme.typography.bodySmall)
                    RadioButton(
                        selected = weatherSource == WeatherDataViewModel.WeatherApi.WEATHER_STACK_API,
                        onClick = {
                            weatherSource = WeatherDataViewModel.WeatherApi.WEATHER_STACK_API
                            viewModel.getLatLngWeather(
                                viewModel.state.value.lat,
                                viewModel.state.value.lng,
                                weatherSource
                            )
                        },
                        modifier = Modifier.padding(2.dp)
                    )
                    Text(text = "Weather Stack API", style = MaterialTheme.typography.bodySmall)
                }
                Row (verticalAlignment = Alignment.CenterVertically){

                }

            }
        }
    }
}

//@Preview
//@Composable
//fun PreviewWeatherScreen() {
//    WeatherScreen(
//        weatherData = LocationWeatherData(
//            name = "Calicut", main = Main(
//                feels_like = 12.0, grnd_level = 1, humidity = 22, pressure = 12, sea_level = 1000,
//                temp = 12.22, temp_max = 14.4, temp_min = 10.0
//            ), coord = Coord(12.0, 77.9), dt = 1212121, visibility = 12
//        )
//    )
//}