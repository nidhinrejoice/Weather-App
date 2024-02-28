package com.nidhin.locationtracker.feature_getweather.data

import com.nidhin.locationtracker.api.OpenWeatherApiService
import com.nidhin.locationtracker.api.WeatherStackApiService
import com.nidhin.locationtracker.feature_getweather.data.dto.openweatherapi.toLocationWeatherData
import com.nidhin.locationtracker.feature_getweather.data.dto.weatherstack.toLocationWeatherData
import com.nidhin.locationtracker.feature_getweather.domain.IGetWeatherRepository
import com.nidhin.locationtracker.feature_getweather.domain.models.LocationWeatherData
import com.nidhin.locationtracker.feature_getweather.presentation.WeatherDataViewModel
import javax.inject.Inject
import javax.inject.Named

class GetWeatherRepository @Inject constructor(
    @Named("0PEN_WEATHER_API_APP_ID") private val openWeatherAppId: String,
    @Named("WEATHER_STACK_API_ACCESS_KEY") private val weatherStackAccessKey: String,
    private val openWeatherApiService: OpenWeatherApiService,
    private val weatherStackApiService: WeatherStackApiService
) : IGetWeatherRepository {


    override suspend fun getLatLngWeather(
        lat: Double,
        lng: Double,
        weatherSource: WeatherDataViewModel.WeatherApi
    ): LocationWeatherData {
        return if (weatherSource is WeatherDataViewModel.WeatherApi.OPEN_WEATHER_API) {
            openWeatherApiService.getLatLngWeather(openWeatherAppId, lat, lng, "metric")
                .toLocationWeatherData()
        } else {
            weatherStackApiService.getLatLngWeather(weatherStackAccessKey, "$lat,$lng", "LatLon")
                .toLocationWeatherData()
        }
    }
}