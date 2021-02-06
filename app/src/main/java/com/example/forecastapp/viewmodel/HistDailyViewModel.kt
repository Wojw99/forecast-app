package com.example.forecastapp.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.forecastapp.model.HistDaily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.forecastapp.model.data.MyDatabase
import com.example.forecastapp.model.repository.HistDailyRepository

class HistDailyViewModel(application: Application): AndroidViewModel(application)
{
    var readAll: LiveData<List<HistDaily>>
    //var readOne: LiveData<List<HistDaily>>
    private val repository: HistDailyRepository

    init
    {
        val historicalDao = MyDatabase.getDatabase(application).historicalDailyDao()
        repository = HistDailyRepository(historicalDao)
        readAll = repository.showall
        //readAll = repository.allweathers
    }

    fun addhistoricalweather(hdmodel: HistDaily)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.add(hdmodel)
        }
    }

    fun edithistoricalweather(hdmodel: HistDaily)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(hdmodel)
        }
    }

    fun deletehistoricalweather(hdmodel: HistDaily)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(hdmodel)
        }
    }

    fun deletallweathers()
    { viewModelScope.launch(Dispatchers.IO) {
        repository.deleteall()
        }
    }
}