package com.example.forecastapp.model

data class WeatherModel(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)