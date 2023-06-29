package com.example.openweather

import com.example.openweather.data.NetworkCompleteWeather
import com.example.openweather.data.NetworkPlaceGeolocation
import com.example.openweather.data.NetworkWeather
import io.reactivex.Single
import retrofit2.Response

const val IMPERIAL = "imperial"
//const val KEY = BuildConfig.API_KEY
//to run locally
const val KEY = "db0f090639f3f7dbc4e02dea86e9b06f"

class WeatherRepository (
    private val api: Api
) : WeatherRepositoryInterface {
    override fun getWeatherByPlace(place: String): Single<NetworkCompleteWeather>{
        return api.getWeatherByPlace(place, IMPERIAL, KEY)
    }

    override fun getWeatherByCoordinates(lat: String, lon: String): Single<NetworkCompleteWeather>{
        return api.getWeatherByCoordinates(lat, lon, IMPERIAL, KEY)
    }

    override fun getLocationCoordinates(place: String, limit: Int): Single<List<NetworkPlaceGeolocation>> {
        return api.getGeoCoordinates(place, limit, KEY)
    }

}