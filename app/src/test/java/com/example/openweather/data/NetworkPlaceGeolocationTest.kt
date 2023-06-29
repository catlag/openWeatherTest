package com.example.openweather.data

import com.example.openweather.domain.PlaceGeolocation
import org.junit.Assert.*
import org.junit.Test

class NetworkPlaceGeolocationTest{

    @Test
    fun getNetworkPlaceGeolocation_toDomain(){
        val places = listOf(
            NetworkPlaceGeolocation(
                "Place 1",
                -45.00,
                -67.00,
                "Usa",
                "California"
            ),
            NetworkPlaceGeolocation(
                "Place 2",
                -12.00,
                -56.00,
                "Usa",
                ""
            )
        )

        val result = places.map { it.toDomain() }

        assertEquals(result.size, 2)
        assertEquals(result.first().javaClass, PlaceGeolocation::class.java)
        assertEquals(result.first().fullLocation, "Place 1 California, Usa")
        assertEquals(result.last().fullLocation, "Place 2, Usa")
    }
}