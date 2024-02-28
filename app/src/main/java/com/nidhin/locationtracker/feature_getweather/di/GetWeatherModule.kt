package com.nidhin.locationtracker.feature_getweather.di

import com.nidhin.locationtracker.feature_getweather.data.GetWeatherRepository
import com.nidhin.locationtracker.feature_getweather.domain.GetLatLngWeather
import com.nidhin.locationtracker.feature_getweather.domain.IGetWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
open class GetWeatherModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(getWeatherRepository: GetWeatherRepository): IGetWeatherRepository {
        return getWeatherRepository
    }


//    @Singleton
//    @Provides
//    open fun provideGetWeatherUsecases(getWeatherRepository: IGetWeatherRepository): PortfolioUsecases {
//        return PortfolioUsecases(
//        )
//    }

    @Singleton
    @Provides
    open fun provideGetLatLngWeatherUseCase(getWeatherRepository: IGetWeatherRepository):GetLatLngWeather{
        return GetLatLngWeather(getWeatherRepository)
    }
}