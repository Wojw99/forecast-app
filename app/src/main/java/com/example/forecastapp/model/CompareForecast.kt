package com.example.forecastapp.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.forecastapp.model.data.MyDatabase
import com.example.forecastapp.model.repository.ForecastRepository
import com.example.forecastapp.model.repository.HistDailyRepository
import com.example.forecastapp.model.welcome.Welcome
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class CompareForecast(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    private val histDailyRepository = HistDailyRepository(MyDatabase.getDatabase(appContext).historicalDailyDao())
    private val forecastRepository = ForecastRepository()

    override fun doWork(): Result {
        for(tag in this.tags){
            // Check if tag is numeric
            if(tag.matches("-?\\d+(\\.\\d+)?".toRegex())){
                GlobalScope.launch {
                    val histDaily = histDailyRepository.selectbyid(tag.toInt())
                    Log.d("Compare", histDaily.city)

                    delay(1000)

                    val checkedHistDaily = forecastRepository.getOneCallForecast(histDaily.lat, histDaily.lon)
                    Log.d("Compare", checkedHistDaily.body().toString())

                    if(checkedHistDaily.body() != null){
                        val accuracy = countAccuracy(histDaily, checkedHistDaily.body()!!)
                        histDaily.accuracy = accuracy.toString()
                        histDailyRepository.update(histDaily)
                    }
                }
            }
        }
        return Result.success()
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