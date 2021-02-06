package com.example.forecastapp.model.repository

import androidx.lifecycle.LiveData
import com.example.forecastapp.model.CheckedHistDaily
import com.example.forecastapp.model.data.CheckedHistDailyDao

class CheckedDailyRepository(private var checkedDailyDao: CheckedHistDailyDao) {
    val showAll: LiveData<List<CheckedHistDaily>> = checkedDailyDao.checkedDailyAll()

    suspend fun add(checkedDaily: CheckedHistDaily)
    {
        checkedDailyDao.add(checkedDaily)
    }

    suspend fun delete(checkedDaily: CheckedHistDaily)
    {
        checkedDailyDao.delete(checkedDaily)
    }

    suspend fun selectbyid(weatherid: Int): CheckedHistDaily
    {
        return checkedDailyDao.selectbyid(weatherid)
    }
}