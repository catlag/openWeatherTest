package com.example.openweather.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openweather.LAT
import com.example.openweather.LON
import com.example.openweather.core.Outcome
import com.example.openweather.domain.CompleteWeather
import com.example.openweather.domain.PlaceGeolocation
import com.example.openweather.ui.main.usecases.FetchLocationCoordinatesUseCase
import com.example.openweather.ui.main.usecases.FetchWeatherByCoordinatesUseCase
import com.example.openweather.ui.main.usecases.FetchWeatherByPlaceUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainViewModel(
    private val fetchWeatherByPlace: FetchWeatherByPlaceUseCase,
    private val fetchLocationCoordinates: FetchLocationCoordinatesUseCase,
    private val fetchWeatherByCoordinates: FetchWeatherByCoordinatesUseCase,
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val placeGeoLocations = MutableLiveData<Outcome<List<PlaceGeolocation>>>()
    var completeWeather = MutableLiveData<Outcome<CompleteWeather>>()

    fun getWeatherByPlace(userInput: String){
        completeWeather.value = Outcome.loading(null)
        compositeDisposable.add(
            fetchWeatherByPlace
                .execute(userInput)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    completeWeather.value = Outcome.success(it)
                }, {
                    completeWeather.value = Outcome.error(it.message.toString(), null)
                    Log.e("WEATHER", it.message.toString())
                })
        )
    }

    fun getWeatherByCoordinates(lat: String, lon: String){
        completeWeather.value = Outcome.loading(null)
        compositeDisposable.add(
            fetchWeatherByCoordinates
                .execute(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                   completeWeather.value = Outcome.success(it)
                }, {
                    completeWeather.value = Outcome.error(it.message.toString(), null)
                    Log.e("WEATHER", it.message.toString())
                })
        )
    }

    fun getLocationCoordinates(userInput: String, limit: Int = 5){
        placeGeoLocations.value = Outcome.loading(listOf())
        compositeDisposable.add(
            fetchLocationCoordinates
                .execute(userInput, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    placeGeoLocations.value = Outcome.success(it)
                }, {
                    placeGeoLocations.value = Outcome.error(it.message.toString(), null)
                    Log.e("COORDINATES", it.message.toString())
                })
        )
    }
}