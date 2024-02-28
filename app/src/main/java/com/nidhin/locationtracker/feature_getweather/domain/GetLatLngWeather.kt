package com.nidhin.locationtracker.feature_getweather.domain

import com.nidhin.locationtracker.feature_getweather.domain.models.LocationWeatherData
import com.nidhin.locationtracker.feature_getweather.presentation.WeatherDataViewModel
import javax.inject.Inject

class GetLatLngWeather @Inject constructor(
    private val getWeatherRepository: IGetWeatherRepository
) {

    suspend operator fun invoke(
        lat: Double,
        lng: Double,
        weatherSource: WeatherDataViewModel.WeatherApi
    ): LocationWeatherData {
        return getWeatherRepository.getLatLngWeather(lat,lng,weatherSource)
    }
}