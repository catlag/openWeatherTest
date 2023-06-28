package com.example.openweather.ui.main.usecases

import com.example.openweather.WeatherRepositoryInterface
import com.example.openweather.domain.CompleteWeather
import io.reactivex.Single

class FetchWeatherByCoordinatesUseCase(private val repository: WeatherRepositoryInterface) {
    fun execute(lat: String, long: String): Single<CompleteWeather> = repository.getWeatherByCoordinates(lat, long).map { it.toDomain() }
}