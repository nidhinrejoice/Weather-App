package com.nidhin.locationtracker.feature_getweather.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nidhin.locationtracker.feature_getweather.domain.GetLatLngWeather
import com.nidhin.locationtracker.feature_getweather.domain.models.LocationWeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDataViewModel @Inject constructor(
    private val getLatLngWeatherUsecase: GetLatLngWeather
) : ViewModel() {


    var isRefreshing = mutableStateOf(false)
        private set
    private var _state = mutableStateOf(WeatherScreenState())
    val state: State<WeatherScreenState> = _state
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getLatLngWeather(lat: Double, lng: Double, weatherSource: WeatherApi= WeatherApi.OPEN_WEATHER_API) {
        isRefreshing.value = true
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(
                    lat = lat, lng = lng,
                    weatherData = getLatLngWeatherUsecase(lat, lng, weatherSource)
                )
                isRefreshing.value = false
            } catch (ex: Exception) {
                isRefreshing.value = false
                _eventFlow.emit(UiEvent.ShowToast(ex.message.toString()))
            }
        }
    }

    data class WeatherScreenState(
        var lat: Double = 0.0,
        var lng: Double = 0.0,
        var weatherData: LocationWeatherData? = null
    )


    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
    }

    sealed class WeatherApi{
        object OPEN_WEATHER_API : WeatherApi()
        object WEATHER_STACK_API : WeatherApi()
    }
}