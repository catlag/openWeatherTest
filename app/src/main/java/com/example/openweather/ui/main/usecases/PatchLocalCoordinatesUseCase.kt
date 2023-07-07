package com.example.openweather.ui.main.usecases

import android.content.SharedPreferences
import com.example.openweather.LAT
import com.example.openweather.LON

class PatchLocalCoordinatesUseCase(private val sharedPreferencesEditor: SharedPreferences.Editor) {
    fun execute(lat: String, lon: String) {
        sharedPreferencesEditor.putString(LAT, lat).toString()
        sharedPreferencesEditor.putString(LON, lon).toString()
        sharedPreferencesEditor.apply()
    }

}