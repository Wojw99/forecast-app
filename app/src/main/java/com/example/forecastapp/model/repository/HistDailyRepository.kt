package com.example.forecastapp.model.repository

import androidx.lifecycle.LiveData
import com.example.forecastapp.model.data.HistoricalDailyDao
import com.example.forecastapp.model.HistDaily

class HistDailyRepository(private var historicalDailyDao: HistoricalDailyDao) {
    val showall: LiveData<List<HistDaily>> = historicalDailyDao.historicaldailyall()

    suspend fun add(hweatherdaily:HistDaily)
    {
        historicalDailyDao.add(hweatherdaily)
    }

    suspend fun update(hweatherdaily: HistDaily){ historicalDailyDao.update(hweatherdaily) }

    suspend fun delete(hweatherdaily:HistDaily)
    {
        historicalDailyDao.delete(hweatherdaily)
    }

    //val allweathers: LiveData<List<HistDaily>> = historicalDailyDao.getallweathers()

    suspend fun deleteall()
    {
        historicalDailyDao.deleteall()
    }

    fun selectbyid(weatherid: Int):LiveData<List<HistDaily>>
    {

        return historicalDailyDao.selectbyid(weatherid)
    }

}