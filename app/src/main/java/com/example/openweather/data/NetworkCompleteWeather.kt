package com.example.openweather.data

import com.example.openweather.domain.*

data class NetworkCompleteWeather(
    val coord: NetworkCoordinates,
    val weather: List<NetworkWeather>,
    val main: NetworkMainWeather,
    val name: String,
){
    fun toDomain(): CompleteWeather = CompleteWeather(
        coordinates = coord.toDomain(),
        weather = weather.map { it.toDomain() },
        mainWeather = main.toDomain(),
        name = name,
    )
}

data class NetworkCoordinates(
    val lon: Double,
    val lat: Double
){
    fun toDomain() = Coordinates(
        lon = lon,
        lat = lat,
    )
}

data class NetworkWeather (
    val id: String,
    val main: String,
    val description: String,
    val icon: String,
) {
    fun toDomain() = Weather(
        id = id,
        main = main,
        description = description.uppercase(),
        icon = icon,
        iconUrl = toIconUrl()
    )

    private fun toIconUrl() : String{
        return "https://openweathermap.org/img/wn/$icon@2x.png"
    }
}

data class NetworkMainWeather(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val humidity: Double,
){
    fun toDomain() = MainWeather(
        temp = temp.toString(),
        feelsLike = feels_like.toString(),
        tempMin = temp_min.toString(),
        tempMax = temp_max.toString(),
        humidity = humidity.toString(),
    )
}




