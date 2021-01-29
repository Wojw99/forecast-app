package com.example.forecastapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forecastapp.model.Welcome
import com.example.forecastapp.model.repository.ForecastRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class ForecastViewModel : ViewModel() {
    val forecastBody: MutableLiveData<Welcome> = MutableLiveData()
    private val forecastRepository = ForecastRepository()

    fun getOneCallForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            // Todo: When lat and lon is same as previous then method calls localRepository with saved forecast
            val response = forecastRepository.getOneCallForecast(lat, lon)

            if(response.isSuccessful){
                forecastBody.value = response.body()!!
                Log.d("Response", response.body()!!.current.weather[0].description)
            }
            else{
                Log.d("Response", response.errorBody().toString())
                Log.d("ErrorCode:", response.code().toString())
            }
        }
    }
}