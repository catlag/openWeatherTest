package com.example.openweather.ui.main.usecases

import com.example.openweather.WeatherRepositoryInterface
import com.example.openweather.domain.CompleteWeather
import io.reactivex.Single
import retrofit2.Response

class FetchWeatherByPlaceUseCase(private val repository: WeatherRepositoryInterface) {
    fun execute(place: String): Single<CompleteWeather> = repository.getWeatherByPlace(place).map { it.toDomain() }
}