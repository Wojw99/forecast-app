package com.example.forecastapp.model.welcome


import com.example.forecastapp.model.hourly.Hourly
import com.example.forecastapp.model.welcome.Current
import com.example.forecastapp.model.welcome.Daily
import com.google.gson.annotations.SerializedName

data class Welcome(
        @SerializedName("current")
    val current: Current,
        @SerializedName("hourly")
    val hourly: Hourly,
        @SerializedName("daily")
    val daily: List<Daily>,
        @SerializedName("lat")
    val lat: Double,
        @SerializedName("lon")
    val lon: Double,
        @SerializedName("timezone")
    val timezone: String,
        @SerializedName("timezone_offset")
    val timezoneOffset: Int
)