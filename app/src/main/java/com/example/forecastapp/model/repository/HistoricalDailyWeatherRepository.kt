package com.example.forecastapp.model.repository

import androidx.lifecycle.LiveData
import com.example.forecastapp.model.Dao.HistoricalDailyDao
import com.example.forecastapp.model.HistoricalDailyModel

class HistoricalDailyWeatherRepository(private var historicalDailyDao: HistoricalDailyDao) {
    val showall: LiveData<List<HistoricalDailyModel>> = historicalDailyDao.historicaldailyall()

    suspend fun add(hweatherdaily:HistoricalDailyModel)
    {
        historicalDailyDao.add(hweatherdaily)
    }

    suspend fun delete(hweatherdaily:HistoricalDailyModel)
    {
        historicalDailyDao.delete(hweatherdaily)
    }


}