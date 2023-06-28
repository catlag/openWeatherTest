package com.example.openweather.domain

data class CompleteWeather(
    val coordinates: Coordinates,
    val weather: List<Weather>,
    val mainWeather: MainWeather,
    val name: String,
)

data class Coordinates(
    val lon: Double,
    val lat: Double
)

data class Weather (
    val id: String,
    val main: String,
    val description: String,
    val icon: String,
    val iconUrl: String,
)

data class MainWeather(
    val temp: String,
    val feelsLike: String,
    val tempMin: String,
    val tempMax: String,
    val humidity: String,
)
