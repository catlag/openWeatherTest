package com.example.openweather.data

import com.example.openweather.domain.CompleteWeather
import com.example.openweather.domain.PlaceGeolocation
import org.junit.Assert.*
import org.junit.Test

class NetworkCompleteWeatherTest{

    @Test
    fun getNetworkCompletedWeather_toDomain(){
        val completeWeather = NetworkCompleteWeather(
            NetworkCoordinates(13.00, -10.000) ,
            listOf(
                NetworkWeather(
                    "123",
                    "Rain",
                    "heavy rain",
                    "d10"
                )
            ),
            NetworkMainWeather(
                55.00,
                65.00,
                55.00,
                70.00,
                13.00
            ),
            "place name"
        )

       val result = completeWeather.toDomain()
        assertEquals(result.weather.first().description, "HEAVY RAIN")
        assertEquals(result.weather.first().iconUrl, "https://openweathermap.org/img/wn/d10@2x.png")
        assertEquals(result.javaClass, CompleteWeather::class.java)
    }


}