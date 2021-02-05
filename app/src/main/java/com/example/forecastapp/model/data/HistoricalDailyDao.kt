package com.example.forecastapp.model.data
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.forecastapp.model.HistDaily

@Dao
interface HistoricalDailyDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(historicalDailyModel: HistDaily)

    @Delete
    suspend fun delete(historicalDailyModel: HistDaily)

    @Query("DELETE FROM tab_historicalweather")
    suspend fun deleteall()

    @Query("SELECT * FROM tab_historicalweather ORDER BY id ASC")
    fun historicaldailyall(): LiveData<List<HistDaily>>
}