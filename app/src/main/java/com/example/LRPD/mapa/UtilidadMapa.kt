package com.example.LRPD.mapas

import android.content.Context
import android.location.LocationManager

object UtilidadMapa {

    fun gpsActivado(context: Context): Boolean {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}