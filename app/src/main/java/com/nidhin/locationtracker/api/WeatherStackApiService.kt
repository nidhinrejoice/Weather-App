package com.nidhin.locationtracker.api

import com.nidhin.locationtracker.feature_getweather.data.dto.openweatherapi.OpenWeatherApiResponse
import com.nidhin.locationtracker.feature_getweather.data.dto.weatherstack.WeatherStackApiResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface WeatherStackApiService {

    @POST("current")
    suspend fun getLatLngWeather(
        @Query("access_key") access_key: String,
        @Query("query") query: String,
        @Query("type") type: String
    ): WeatherStackApiResponse

}