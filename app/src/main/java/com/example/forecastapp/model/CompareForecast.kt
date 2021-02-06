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

class CompareForecast(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    private val histDailyRepository = HistDailyRepository(MyDatabase.getDatabase(appContext).historicalDailyDao())
    private val checkedDailyRepository = CheckedDailyRepository(MyDatabase.getDatabase(appContext).checkedHistDailyDao())
    private val forecastRepository = ForecastRepository()

    override fun doWork(): Result {
        for(tag in this.tags){
            // Check if tag is numeric
            if(tag.matches("-?\\d+(\\.\\d+)?".toRegex())){
                GlobalScope.launch {
                    val histDaily = histDailyRepository.selectbyid(tag.toInt())
                    Log.d("Compare", histDaily.city)

                    delay(1000)

                    val welcome = forecastRepository.getOneCallForecast(histDaily.lat, histDaily.lon)
                    Log.d("Compare", welcome.body().toString())

                    if(welcome.body() != null){
                        val accuracy = countAccuracy(histDaily, welcome.body()!!)
                        histDaily.accuracy = accuracy.toString()
                        histDailyRepository.update(histDaily)
                        addToCheckedDaily(welcome.body()!!, histDaily)
                    }
                }
            }
        }
        return Result.success()
    }

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

    private fun countAccuracy(daily1: HistDaily, daily2: Welcome): Double{
        var count = 0.0

        if(daily1.temp == daily2.current.temp) count += 1
        if(daily1.humidity == daily2.current.humidity) count += 1
        if(daily1.pressure == daily2.current.pressure) count += 1
        if(daily1.wind_speed == daily2.current.windSpeed) count += 1
        if(daily1.wind_deg == daily2.current.windDeg) count += 1
        if(daily1.clouds == daily2.current.clouds) count += 1
        if(daily1.uvi == daily2.current.uvi) count += 1
        if(daily1.weather == daily2.current.weather[0].description) count += 1

        return count / 8.0
    }
}