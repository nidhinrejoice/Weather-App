package com.nidhin.locationtracker.feature_getweather.data.dto.openweatherapi

import com.nidhin.locationtracker.feature_getweather.domain.models.LocationWeatherData

data class OpenWeatherApiResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

fun OpenWeatherApiResponse.toLocationWeatherData():LocationWeatherData{
    return LocationWeatherData(
        coord = this.coord,
        name = this.name,
        dt = this.dt,
        main = this.main,
        visibility = this.visibility,
        wind = this.wind,
        weather = this.weather
    )
}