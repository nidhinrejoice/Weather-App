package com.nidhin.locationtracker

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.nidhin.locationtracker.feature_getweather.presentation.WeatherDataViewModel
import com.nidhin.locationtracker.feature_getweather.presentation.WeatherScreen
import com.nidhin.locationtracker.ui.theme.LocationTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    var trackingService: LocationTrackingService? = null
    private var isBound = false

    override fun onStop() {
        super.onStop()
        if (isBound)
            unbindService(serviceConnection)
        isBound = false
    }

    private val viewModel: WeatherDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocationTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        val context = LocalContext.current

                        val permissionGranted = remember { mutableStateOf(false) }

                        LocationPermissionRequest {
                            permissionGranted.value = true
                        }

//                        SwipeRefresh(
//                            state = rememberSwipeRefreshState(viewModel.isRefreshing.value),
//                            onRefresh = {
//                                viewModel.getLatLngWeather(
//                                    viewModel.state.value.lat,
//                                    viewModel.state.value.lng
//                                )
//                            },
//                        ) {
                        Column {
                            if (permissionGranted.value) {
                                val intent =
                                    Intent(context, LocationTrackingService::class.java)
                                context.bindService(
                                    intent,
                                    serviceConnection,
                                    Context.BIND_AUTO_CREATE
                                )
                                viewModel.state.value.weatherData?.let { WeatherScreen(it) }
                            }
                        }
                    }
//                    }
                }
            }
        }
    }

    private fun unbindLocationService() {
        unbindService(serviceConnection)
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as LocationTrackingService.LocalBinder
            trackingService = binder.getService()
            isBound = true
            trackingService?.getCurrentLocation { location ->
                location?.let {
                    viewModel.getLatLngWeather(it.latitude, it.longitude)
                }
            }

        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }

    }

    private fun getLocation() {
        val intent = Intent(this, LocationTrackingService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionRequest(onPermissionGranted: () -> Unit) {
    // Remember the permission state
    val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    when {
        permissionState.status.isGranted -> {
            // Permission is granted, you can perform the location-related operation
            onPermissionGranted()
        }

        permissionState.status.shouldShowRationale -> {
            // Explain to the user why the permission is needed
            // and request the permission again upon user confirmation
            Button(onClick = { permissionState.launchPermissionRequest() }) {
                Text("Location permission is needed to access your current location. Please grant the permission.")
            }
        }

        else -> {
            // Permission has not been requested yet or was denied without asking never again,
            // you can request the permission
            Button(onClick = { permissionState.launchPermissionRequest() }) {
                Text("Request Location Permission")
            }
        }
    }
}