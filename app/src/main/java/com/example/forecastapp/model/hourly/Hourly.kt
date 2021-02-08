package com.example.forecastapp.model.hourly

import com.example.forecastapp.model.welcome.Weather
import com.google.gson.annotations.SerializedName

data class Hourly (
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    @SerializedName("uvi")
    val uvi: Double,
    @SerializedName("clouds")
    val clouds: Double,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind_deg")
    val windDeg: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("pop")
    val pop: Double
)