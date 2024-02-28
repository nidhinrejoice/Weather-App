package com.nidhin.locationtracker.api

import com.nidhin.locationtracker.feature_getweather.data.dto.openweatherapi.OpenWeatherApiResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface OpenWeatherApiService {

    @POST("data/2.5/weather")
    suspend fun getLatLngWeather(
        @Query("appid") appid: String,
        @Query("lat") lat: Double,
        @Query("lon") lng: Double,
        @Query("units") units: String
    ): OpenWeatherApiResponse

}