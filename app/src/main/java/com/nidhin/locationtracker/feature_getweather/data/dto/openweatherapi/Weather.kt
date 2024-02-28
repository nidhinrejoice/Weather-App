package com.nidhin.locationtracker.feature_getweather.data.dto.openweatherapi

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)