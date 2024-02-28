package com.nidhin.locationtracker.feature_getweather.data.dto.weatherstack

data class Request(
    val language: String,
    val query: String,
    val type: String,
    val unit: String
)