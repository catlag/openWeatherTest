package com.example.openweather

import com.example.openweather.ui.main.usecases.FetchWeatherByPlaceUseCase
import com.example.openweather.ui.main.MainViewModel
import com.example.openweather.ui.main.usecases.FetchLocationCoordinatesUseCase
import com.example.openweather.ui.main.usecases.FetchWeatherByCoordinatesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val weatherModule = module {
    single<WeatherRepositoryInterface> {
        WeatherRepository(api = get())
    }

    single{
        FetchWeatherByPlaceUseCase(
            repository = get()
        )
    }

    single {
        FetchLocationCoordinatesUseCase(
            repository = get()
        )
    }

    single {
        FetchWeatherByCoordinatesUseCase(
            repository = get()
        )
    }

    viewModel {
        MainViewModel(
            fetchWeatherByPlace = get(),
            fetchLocationCoordinates = get(),
            fetchWeatherByCoordinates = get(),
        )
    }
}