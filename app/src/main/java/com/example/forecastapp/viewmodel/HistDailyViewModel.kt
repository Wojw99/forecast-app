package com.example.forecastapp.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.forecastapp.model.CheckedHistDaily
import com.example.forecastapp.model.HistDaily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.forecastapp.model.data.MyDatabase
import com.example.forecastapp.model.repository.CheckedDailyRepository
import com.example.forecastapp.model.repository.HistDailyRepository

class HistDailyViewModel(application: Application): AndroidViewModel(application)
{
    var histDaily: MutableLiveData<HistDaily> = MutableLiveData()
    var checkedHistDaily: MutableLiveData<CheckedHistDaily> = MutableLiveData()

    private val histRepository: HistDailyRepository
    private val checkRepository: CheckedDailyRepository

    init
    {
        val historicalDao = MyDatabase.getDatabase(application).historicalDailyDao()
        val checkedDao = MyDatabase.getDatabase(application).checkedHistDailyDao()
        histRepository = HistDailyRepository(historicalDao)
        checkRepository = CheckedDailyRepository(checkedDao)
    }

    fun readHistDailyById(id: Int){
        viewModelScope.launch {
            histDaily.value = histRepository.selectbyid(id)
        }
    }

    fun readCheckedHistDailyById(id: Int){
        viewModelScope.launch {
            checkedHistDaily.value = checkRepository.selectbyid(id)
        }
    }

    companion object{
        // ID of the current showed HistDaily and CheckedDaily
        var currentId = 0
    }
}