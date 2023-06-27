package com.example.openweather.ui.main.usecases

import com.example.openweather.WeatherRepositoryInterface
import com.example.openweather.data.toDomain
import com.example.openweather.domain.PlaceGeolocation
import io.reactivex.Single
import retrofit2.Response

class FetchLocationCoordinatesUseCase(private val repository: WeatherRepositoryInterface) {
    fun execute(place: String, limit: Int): Single<List<PlaceGeolocation>> = repository.getLocationCoordinates(place, limit).map { it.toDomain() }
}