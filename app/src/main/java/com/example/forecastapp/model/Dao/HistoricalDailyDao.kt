package com.example.forecastapp.model.Dao
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.forecastapp.model.HistoricalDailyModel

@Dao
interface HistoricalDailyDao
{ //realweatherdailydao
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(historicalDailyModel: HistoricalDailyModel)

    @Delete
    suspend fun delete(historicalDailyModel: HistoricalDailyModel)

    @Query("DELETE FROM tab_historicalweather")
    suspend fun deleteall()

    @Query("SELECT * FROM tab_historicalweather ORDER BY id ASC")
    fun historicaldailyall(): LiveData<List<HistoricalDailyModel>>
}