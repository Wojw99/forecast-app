package com.example.forecastapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forecastapp.model.Welcome
import com.example.forecastapp.model.repository.ForecastRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class ForecastViewModel : ViewModel() {
    val forecastResponse: MutableLiveData<Response<Welcome>> = MutableLiveData()
    private val forecastRepository = ForecastRepository()

    fun getOneCallForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            val response = forecastRepository.getOneCallForecast(lat, lon)
            forecastResponse.value = response
        }
    }
}