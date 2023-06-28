package com.example.openweather

import com.example.openweather.data.NetworkCompleteWeather
import com.example.openweather.data.NetworkPlaceGeolocation
import com.example.openweather.data.NetworkWeather
import io.reactivex.Single
import retrofit2.Response

const val IMPERIAL = "imperial"
class WeatherRepository (
    private val api: Api
) : WeatherRepositoryInterface {
    override fun getWeatherByPlace(place: String): Single<NetworkCompleteWeather>{
        return api.getWeatherByPlace(place, IMPERIAL, BuildConfig.API_KEY)
    }

    override fun getWeatherByCoordinates(lat: String, lon: String): Single<NetworkCompleteWeather>{
        return api.getWeatherByCoordinates(lat, lon, IMPERIAL, BuildConfig.API_KEY)
    }

    override fun getLocationCoordinates(place: String, limit: Int): Single<List<NetworkPlaceGeolocation>> {
        return api.getGeoCoordinates(place, limit, BuildConfig.API_KEY)
    }

}