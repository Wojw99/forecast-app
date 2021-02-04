package com.example.forecastapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forecastapp.model.welcome.Welcome
import com.example.forecastapp.model.repository.ForecastRepository
import kotlinx.coroutines.launch

class ListViewModel:ViewModel() {
    val forecastBody: LiveData<Welcome> = MutableLiveData()
    private val forecastRepository = ForecastRepository()

    fun getOneCallForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            val response = forecastRepository.getOneCallForecast(lat, lon)
        }
    }
}