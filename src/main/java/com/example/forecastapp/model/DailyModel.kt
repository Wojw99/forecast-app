package com.example.forecastapp.model

data class DailyModel (
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: TempModel,
    val feels_like: FeelsLikeModel,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Double,
    val wind_speed: Double,
    val wind_deg: Double,
    val weather: List<WeatherModel>,
    val clouds: Double,
    val pop: Double,
    val uvi: Double
)