
package com.example.forecastapp.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "tab_checkedweather")
data class CheckedHistDaily(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val dt: Int,
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
    val uvi: Double,
    val checked: Boolean
)