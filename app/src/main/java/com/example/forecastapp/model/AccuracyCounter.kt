package com.example.forecastapp.model

import android.util.Log
import com.example.forecastapp.model.welcome.Welcome

class AccuracyCounter {
    /**
     * Counts accuracy by comparing HistDaily model and CheckedHistDaily model.
     * First, calculates accuracy for every single value:
     * a = abs(d) / a
     * where:
     * a - difference of max and min value in the region for given period
     * d - difference of real and forecast value
     * Next, calculate average of values and return it.
     * */
    fun countAccuracy(histDaily: HistDaily, checkedHistDaily: Welcome): Double{
        var count = 0.0

        val tempA = calculateSingleAccuracy(histDaily.temp, checkedHistDaily.current.temp, 20.0)
        count += tempA
        Log.d("Accuracy (temp) : ", tempA.toString())

        val humA = calculateSingleAccuracy(histDaily.humidity, checkedHistDaily.current.humidity, 100)
        count += humA
        Log.d("Accuracy (hum) : ", humA.toString())

        val pressA = calculateSingleAccuracy(histDaily.pressure, checkedHistDaily.current.pressure, 80)
        count += pressA
        Log.d("Accuracy (pressure) : ", pressA.toString())

        val windSpA = calculateSingleAccuracy(histDaily.wind_speed, checkedHistDaily.current.windSpeed, 288.0)
        count += windSpA
        Log.d("Accuracy (wind s) : ", windSpA.toString())

        val windDegA = calculateSingleAccuracy(histDaily.wind_deg, checkedHistDaily.current.windDeg, 360.0)
        count += windDegA
        Log.d("Accuracy (wind d) : ", windDegA.toString())

        val cloudsA = calculateSingleAccuracy(histDaily.clouds, checkedHistDaily.current.clouds, 100.0)
        count += cloudsA
        Log.d("Accuracy (clouds) : ", cloudsA.toString())

        val uviA = calculateSingleAccuracy(histDaily.uvi, checkedHistDaily.current.uvi, 2.0)
        count += uviA
        Log.d("Accuracy (uvi) : ", uviA.toString())

        return (count / 7.0) * 100
    }

    /**
     * Counting accuracy for single value.
     * */
    private fun calculateSingleAccuracy(forecastValue: Int, realValue: Int, amplitude: Int) : Double{
        return calculateSingleAccuracy(forecastValue.toDouble(), realValue.toDouble(), amplitude.toDouble())
    }

    /**
     * Counting accuracy for single value.
     * */
    private fun calculateSingleAccuracy(forecastValue: Double, realValue: Double, amplitude: Double) : Double{
        val diff = kotlin.math.abs(realValue - forecastValue)
        return 1 - (diff / amplitude)
    }
}