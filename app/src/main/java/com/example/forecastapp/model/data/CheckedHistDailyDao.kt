package com.example.forecastapp.model.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.forecastapp.model.CheckedHistDaily

@Dao
interface CheckedHistDailyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(checkedHistDaily: CheckedHistDaily)

    @Delete
    suspend fun delete(checkedHistDaily: CheckedHistDaily)

    @Query("SELECT * FROM tab_checkedweather ORDER BY id ASC")
    fun checkedDailyAll(): LiveData<List<CheckedHistDaily>>

    @Query("SELECT * FROM tab_checkedweather WHERE id=:id ")
    suspend fun selectbyid(id: Int): CheckedHistDaily
}