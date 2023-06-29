package com.example.openweather.data

import android.graphics.Point
import com.example.openweather.domain.PlaceGeolocation
import java.lang.StringBuilder

data class NetworkPlaceGeolocation (
    val name: String,
    val lon: Double,
    val lat: Double,
    val country: String?,
    val state: String?,
){
    fun toDomain() = PlaceGeolocation(
        name = name,
        lon = lon,
        lat = lat,
        country = country,
        state = state,
        fullLocation = getFullLocation()
    )
    private fun getFullLocation(): String {
        val location = StringBuilder()
        if (name.isNotEmpty()){location.append(name)}
        if (state?.isNotEmpty() == true){location.append(" $state")}
        if (country?.isNotEmpty() == true){location.append(", $country")}
        return location.toString()
    }
}

fun List<NetworkPlaceGeolocation>.toDomain(): ArrayList<PlaceGeolocation> {
    var list = ArrayList<PlaceGeolocation>()
    forEach {
        list.add(it.toDomain())
    }
    return list
}
