package com.example.forecastapp.model

data class CurrentModel (
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Double,
    val uvi: Double,
    val clouds: Double,
    val visibility: Int,
    val wind_speed: Double,
    val wind_deg: Double,
    val wind_gust: Double,
    val weather: List<WeatherModel>
)