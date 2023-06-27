package com.example.openweather.ui.main.usecases

import com.example.openweather.WeatherRepositoryInterface
import io.reactivex.Single
import retrofit2.Response

class FetchWeatherByPlaceUseCase(private val repository: WeatherRepositoryInterface) {
    fun execute(place: String): Single<Response<Any>> = repository.getWeatherByPlace(place)
}