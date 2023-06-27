package com.example.openweather

import com.example.openweather.data.NetworkPlaceGeolocation
import com.example.openweather.domain.PlaceGeolocation
import io.reactivex.Single
import retrofit2.Response

interface WeatherRepositoryInterface {

    fun getWeatherByPlace(place: String): Single<Response<Any>>

    fun getIcons(): Single<Any>

    fun getLocationCoordinates(place: String, limit: Int): Single<List<NetworkPlaceGeolocation>>

}