package com.example.openweather.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openweather.core.Outcome
import com.example.openweather.ui.main.usecases.FetchWeatherByPlaceUseCase
import com.example.openweather.domain.PlaceGeolocation
import com.example.openweather.ui.main.usecases.FetchLocationCoordinatesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val fetchWeatherByPlace: FetchWeatherByPlaceUseCase,
    private val fetchLocationCoordinates: FetchLocationCoordinatesUseCase,
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val coordinates = MutableLiveData<Outcome<List<PlaceGeolocation>>>()


    fun getWeatherByPlace(userInput: String){
        compositeDisposable.add(
            fetchWeatherByPlace
                .execute(userInput)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i("WEATHER", it.toString())
                }, {
                    Log.e("WEATHER", it.message.toString())
                })
        )
 }

    fun getLocationCoordinates(userInput: String, limit: Int = 5){
        coordinates.value = Outcome.loading(listOf())
        compositeDisposable.add(
            fetchLocationCoordinates
                .execute(userInput, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                   coordinates.value = Outcome.success(it)
                }, {
                    coordinates.value = Outcome.error(it.message.toString(), null)
                    Log.e("COORDINATES", it.message.toString())
                })
        )
    }
}