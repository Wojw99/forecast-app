package com.example.forecastapp.model.repository

import androidx.lifecycle.LiveData
import com.example.forecastapp.model.data.HistoricalDailyDao
import com.example.forecastapp.model.HistDaily
import retrofit2.Call

class HistDailyRepository(private var historicalDailyDao: HistoricalDailyDao) {
    val showall: LiveData<List<HistDaily>> = historicalDailyDao.historicaldailyall()

    suspend fun add(hweatherdaily:HistDaily)
    {
        historicalDailyDao.add(hweatherdaily)
    }

    suspend fun update(hweatherdaily: HistDaily){ historicalDailyDao.update(hweatherdaily) }

    suspend fun delete(hweatherdaily: HistDaily)
    {
        historicalDailyDao.delete(hweatherdaily)
    }

    //val allweathers: LiveData<List<HistDaily>> = historicalDailyDao.getallweathers()

    suspend fun deleteall()
    {
        historicalDailyDao.deleteall()
    }

    suspend fun selectbyid(weatherid: Int): HistDaily
    {
        return historicalDailyDao.selectbyid(weatherid)
    }

    suspend fun selectbydt(weatherdt: Int): HistDaily
    {
        return historicalDailyDao.selectbydt(weatherdt)
    }

    suspend fun selectMaxId(): Int{
        return historicalDailyDao.selectMaxID()
    }
}