package com.example.openweather.domain

import android.graphics.Point

data class PlaceGeolocation(
    val name: String,
    val lon: Double,
    val lat: Double,
    val country: String?,
    val state: String?,
    val fullLocation: String,
)