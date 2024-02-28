package com.nidhin.locationtracker.feature_getweather.data.dto.openweatherapi

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)