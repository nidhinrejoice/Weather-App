package com.nidhin.locationtracker.feature_getweather.data.dto.weatherstack

import com.nidhin.locationtracker.feature_getweather.data.dto.openweatherapi.Coord
import com.nidhin.locationtracker.feature_getweather.data.dto.openweatherapi.Main
import com.nidhin.locationtracker.feature_getweather.data.dto.openweatherapi.Weather
import com.nidhin.locationtracker.feature_getweather.data.dto.openweatherapi.Wind
import com.nidhin.locationtracker.feature_getweather.domain.models.LocationWeatherData

data class WeatherStackApiResponse(
    val current: Current,
    val location: Location,
    val request: Request
)
fun WeatherStackApiResponse.toLocationWeatherData(): LocationWeatherData {
    return LocationWeatherData(
        coord = Coord(this.location.lat.toDouble(),this.location.lon.toDouble()),
        name = this.location.name,
        dt = this.location.localtime_epoch,
        main = Main(feels_like = this.current.feelslike.toDouble(), grnd_level = 0,
            humidity = this.current.humidity, pressure = this.current.pressure, sea_level = 0, temp = this.current.temperature.toDouble(),
            temp_min = this.current.temperature.toDouble(), temp_max = this.current.temperature.toDouble()),
        visibility = this.current.visibility,
        wind = Wind(deg = this.current.wind_degree, gust = 0.0, speed = this.current.wind_speed.toDouble()),
        weather = listOf( Weather(description = this.current.weather_descriptions.toString(), icon = this.current.weather_icons[0],
            id = 1, main = this.current.weather_descriptions.toString()))
    )
}