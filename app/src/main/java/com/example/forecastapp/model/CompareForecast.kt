package com.example.forecastapp.model

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.forecastapp.model.data.MyDatabase
import com.example.forecastapp.model.repository.CheckedDailyRepository
import com.example.forecastapp.model.repository.ForecastRepository
import com.example.forecastapp.model.repository.HistDailyRepository
import com.example.forecastapp.model.welcome.Welcome
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Class uses WorkManager and is used for checking weather in the background when the
 * application is turned off.
 * */
class CompareForecast(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    private val histDailyRepository = HistDailyRepository(MyDatabase.getDatabase(appContext).historicalDailyDao())
    private val checkedDailyRepository = CheckedDailyRepository(MyDatabase.getDatabase(appContext).checkedHistDailyDao())
    private val forecastRepository = ForecastRepository()

    /**
     * Starts when the given interval of time is up. The function gets a new forecast
     * and compares it to the historical forecast saved in the database. Then the new forecast
     * is saved.
     * */
    override fun doWork(): Result {
        for(tag in this.tags){
            // Check if tag is numeric. Numeric tag is a interval of time
            if(tag.matches("-?\\d+(\\.\\d+)?".toRegex())){
                GlobalScope.launch {
                    val histDaily = histDailyRepository.selectbyid(tag.toInt())
                    Log.d("Compare", histDaily.city)

                    delay(1000)

                    val welcome = forecastRepository.getOneCallForecast(histDaily.lat, histDaily.lon)
                    Log.d("Compare", welcome.body().toString())

                    if(welcome.body() != null){
                        val accuracyCounter = AccuracyCounter()
                        val accuracy = accuracyCounter.countAccuracy(histDaily, welcome.body()!!)
                        val decForm = DecimalFormat("#.##")
                        decForm.roundingMode = RoundingMode.CEILING
                        histDaily.accuracy = decForm.format(accuracy)

                        histDailyRepository.update(histDaily)

                        addToCheckedDaily(welcome.body()!!, histDaily)
                    }
                }
            }
        }
        return Result.success()
    }

    /**
     * Adds a new model to the checked table in the history database
     * */
    private suspend fun addToCheckedDaily(welcome: Welcome, histDaily: HistDaily){
        val current = welcome.current
        val checkedHistDaily = CheckedHistDaily(
                id = histDaily.id,
                dt = current.dt,
                sunrise = current.sunrise,
                sunset = current.sunset,
                temp = current.temp,
                feels_like = current.feelsLike,
                pressure = current.pressure,
                humidity = current.humidity,
                dew_point = current.dewPoint,
                wind_deg = current.windDeg,
                wind_speed = current.windSpeed,
                weather = current.weather[0].description,
                clouds = current.clouds,
                uvi = current.uvi,
                checked = true
        )
        checkedDailyRepository.add(checkedHistDaily)
    }
}