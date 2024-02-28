package com.nidhin.locationtracker.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.nidhin.locationtracker.Constants
import com.nidhin.locationtracker.api.OpenWeatherApiService
import com.nidhin.locationtracker.api.WeatherStackApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    open fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
        return builder.build()
    }

    @Singleton
    @Provides
    open fun provideOpenWeatherApi(
        okHttpClient: OkHttpClient,
        @Named("0PEN_WEATHER_API_ENDPOINT") API_END_POINT: String
    ): OpenWeatherApiService {

        val retrofit = Retrofit.Builder()
            .baseUrl(API_END_POINT)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(OpenWeatherApiService::class.java)
    }

    @Singleton
    @Provides
    open fun provideWeatherStackApi(
        okHttpClient: OkHttpClient,
        @Named("WEATHER_STACK_API_ENDPOINT") API_END_POINT: String
    ): WeatherStackApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_END_POINT)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(WeatherStackApiService::class.java)
    }

    @Singleton
    @Provides
    open fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Named("0PEN_WEATHER_API_ENDPOINT")
    fun provideOpenWeatherApiBaseUrl(): String {
        return "https://api.openweathermap.org/"
    }

    @Provides
    @Named("WEATHER_STACK_API_ENDPOINT")
    fun provideWeatherStackBaseUrl(): String {
        return "http://api.weatherstack.com/"
    }

    @Provides
    @Named("0PEN_WEATHER_API_APP_ID")
    fun provideOpenWeatherApiAppId(): String {
        return Constants.openWeatherApiAppId
    }

    @Provides
    @Named("WEATHER_STACK_API_ACCESS_KEY")
    fun provideWeatherStackAccessKey(): String {
        return Constants.weatherStackAccessKey
    }


}