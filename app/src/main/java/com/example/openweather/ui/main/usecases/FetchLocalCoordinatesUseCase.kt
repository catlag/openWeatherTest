package com.example.openweather.ui.main.usecases

import android.content.SharedPreferences
import com.example.openweather.LAT
import com.example.openweather.LON

class FetchLocalCoordinatesUseCase(private val sharedPreferences: SharedPreferences) {
    fun execute(): HashMap<String, String> {
        val lat = sharedPreferences.getString(LAT, "").toString()
        val lon = sharedPreferences.getString(LON, "").toString()
        return hashMapOf(LAT to lat, LON to lon)
    }

}