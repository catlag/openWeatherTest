package com.example.openweather.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openweather.LAT
import com.example.openweather.LON
import com.example.openweather.core.Outcome
import com.example.openweather.domain.CompleteWeather
import com.example.openweather.domain.PlaceGeolocation
import com.example.openweather.ui.main.usecases.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainViewModel(
    private val fetchWeatherByPlace: FetchWeatherByPlaceUseCase,
    private val fetchLocationCoordinates: FetchLocationCoordinatesUseCase,
    private val fetchWeatherByCoordinates: FetchWeatherByCoordinatesUseCase,
    private val fetchLocalCoordinatesUseCase: FetchLocalCoordinatesUseCase,
    private val patchLocalCoordinatesUseCase: PatchLocalCoordinatesUseCase,
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val placeGeoLocations = MutableLiveData<Outcome<List<PlaceGeolocation>>>()
    var completeWeather = MutableLiveData<Outcome<CompleteWeather>>()
    var localCoordinates = MutableLiveData<Outcome<HashMap<String, String>>>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

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
                   patchLocalCoordinates(it.coordinates.lat.toString(), it.coordinates.lon.toString())
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

    fun getLocalCoordinates(){
        localCoordinates.value = Outcome.loading(hashMapOf())
        val data = fetchLocalCoordinatesUseCase.execute()
        if (data.isNotEmpty()){
            localCoordinates.value = Outcome.success(data)
        }else {
            Outcome.error("missing coordinates", null)
        }
    }

    fun patchLocalCoordinates(lat: String, lon: String){
        patchLocalCoordinatesUseCase.execute(lat, lon)
    }
}