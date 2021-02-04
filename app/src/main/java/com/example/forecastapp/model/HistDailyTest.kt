
package com.example.forecastapp.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

data class HistDailyTest(
    val id: Int,
    val dt: Int,
    val forecastDate: Date,
    val savingDate: Date,
    val city: String,
    val lat: Double,
    val lon: Double,
    val accuracy: String,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Double,
    val wind_speed: Double,
    val wind_deg: Double,
    val weather: String,
    val clouds: Double,
    val pop: Double,
    val uvi: Double
)