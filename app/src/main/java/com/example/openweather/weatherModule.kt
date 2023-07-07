package com.example.openweather

import android.content.SharedPreferences
import com.example.openweather.ui.main.MainViewModel
import com.example.openweather.ui.main.usecases.*
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

    single{
        FetchLocalCoordinatesUseCase(
            get()
        )
    }

    single{
        PatchLocalCoordinatesUseCase(
            get()
        )
    }

    viewModel {
        MainViewModel(
            fetchWeatherByPlace = get(),
            fetchLocationCoordinates = get(),
            fetchWeatherByCoordinates = get(),
            fetchLocalCoordinatesUseCase = get(),
            patchLocalCoordinatesUseCase = get(),
        )
    }
}