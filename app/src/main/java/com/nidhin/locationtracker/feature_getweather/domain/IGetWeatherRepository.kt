package com.nidhin.locationtracker.feature_getweather.domain

import com.nidhin.locationtracker.feature_getweather.domain.models.LocationWeatherData
import com.nidhin.locationtracker.feature_getweather.presentation.WeatherDataViewModel

interface IGetWeatherRepository {

    suspend fun getLatLngWeather(
        lat: Double,
        lng: Double,
        weatherSource: WeatherDataViewModel.WeatherApi
    ) : LocationWeatherData
}