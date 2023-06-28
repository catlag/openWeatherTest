package com.example.openweather

import com.example.openweather.data.NetworkCompleteWeather
import com.example.openweather.data.NetworkPlaceGeolocation
import com.example.openweather.data.NetworkWeather
import com.example.openweather.domain.PlaceGeolocation
import io.reactivex.Single
import retrofit2.Response

interface WeatherRepositoryInterface {

    fun getWeatherByPlace(place: String): Single<NetworkCompleteWeather>

    fun getWeatherByCoordinates(lat: String, long: String): Single<NetworkCompleteWeather>

    fun getLocationCoordinates(place: String, limit: Int): Single<List<NetworkPlaceGeolocation>>

}