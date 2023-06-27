package com.example.openweather.data

import android.graphics.Point
import com.example.openweather.domain.PlaceGeolocation

data class NetworkPlaceGeolocation (
    val name: String,
    val lon: Double,
    val lat: Double,
    val country: String,
    val state: String,
){
    fun toDomain() = PlaceGeolocation(
        name = name,
        lon = lon,
        lat = lat,
        country = country,
        state = state,
    )
}

fun List<NetworkPlaceGeolocation>.toDomain(): ArrayList<PlaceGeolocation> {
    var list = ArrayList<PlaceGeolocation>()
    forEach {
        list.add(it.toDomain())
    }
    return list
}