package com.example.openweather

import com.example.openweather.data.NetworkPlaceGeolocation
import io.reactivex.Single
import retrofit2.Response

class WeatherRepository (
    private val api: Api
) : WeatherRepositoryInterface {
    override fun getWeatherByPlace(place: String): Single<Response<Any>> {
        return api.getWeatherByPlace(place, BuildConfig.API_KEY)
    }

    override fun getIcons(): Single<Any> {
        return api.getIcons()
    }

    override fun getLocationCoordinates(place: String, limit: Int): Single<List<NetworkPlaceGeolocation>> {
        return api.getGeoCoordinates(place, limit, BuildConfig.API_KEY)
    }

}