package com.nidhin.locationtracker.feature_getweather.domain.models

import com.nidhin.locationtracker.feature_getweather.data.dto.openweatherapi.Coord
import com.nidhin.locationtracker.feature_getweather.data.dto.openweatherapi.Main
import com.nidhin.locationtracker.feature_getweather.data.dto.openweatherapi.Weather
import com.nidhin.locationtracker.feature_getweather.data.dto.openweatherapi.Wind

data class LocationWeatherData(

    val coord: Coord,
    val dt: Int,
//    val id: Int,
    val main: Main,
    val name: String,
//    val sys: Sys,
//    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)
