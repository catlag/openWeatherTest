package com.example.openweather

import com.example.openweather.data.NetworkPlaceGeolocation
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("/data/2.5/weather")
    fun getWeatherByPlace(
        @Query("q") city: String,
        @Query("appId") apiKey: String,
    ): Single<Response<Any>>

    @GET("/geo/1.0/direct")
    fun getGeoCoordinates(
        @Query("q") city: String,
        @Query("limit") limit: Int,
        @Query("appId") apiKey: String,
    ): Single<List<NetworkPlaceGeolocation>>

    @GET("/weather-conditions")
    fun getIcons(): Single<Any>
}