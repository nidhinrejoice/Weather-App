package com.nidhin.locationtracker

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Binder
import android.os.IBinder

class LocationTrackingService : Service(), LocationListener {

    private val binder= LocalBinder()
    private lateinit var locationManager: LocationManager
    private var locationListener: LocationListener? = null
    inner class LocalBinder: Binder(){
            fun getService():LocationTrackingService = this@LocationTrackingService
    }
    override fun onBind(p0: Intent): IBinder {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null)
        }catch (ex:SecurityException){

        }
        return binder
    }
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(callback: (Location?) -> Unit) {
        val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastKnownLocation != null) {
            callback(lastKnownLocation)
        } else {
            // Setup a location listener to wait for the current location
            locationListener = LocationListener { location ->
                callback(location)
                // Ensure you remove the listener after receiving an update
                locationManager.removeUpdates(this)
            }.also {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, it)
            }
        }
    }
    override fun onLocationChanged(location: Location) {
    }

    override fun onDestroy() {
        super.onDestroy()
        locationListener?.let { locationManager.removeUpdates(it) }
    }
}