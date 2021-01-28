package com.example.forecastapp.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.forecastapp.model.HistoricalDailyModel
import com.example.forecastapp.model.MyDatabase
import com.example.forecastapp.model.repository.HistoricalDailyWeatherRepository

class HistoricalDailyWeatherViewModel(application: Application):AndroidViewModel(application)
{
    val historicalall: LiveData<List<HistoricalDailyModel>>

    private val historicalrepository: HistoricalDailyWeatherRepository


    init
    {
        val historicalDao = MyDatabase.getDatabase(
            application
        ).historicalDailyDao()
        historicalrepository = HistoricalDailyWeatherRepository(historicalDao)
        historicalall = historicalrepository.showall
    }

    fun addhistoricalweather(hdmodel: HistoricalDailyModel)
    {
        viewModelScope.launch(Dispatchers.IO) {
            historicalrepository.add(hdmodel)
        }
    }

    fun deletehistoricalweather(hdmodel: HistoricalDailyModel)
    {
        viewModelScope.launch(Dispatchers.IO) {
            historicalrepository.delete(hdmodel)
        }
    }
}