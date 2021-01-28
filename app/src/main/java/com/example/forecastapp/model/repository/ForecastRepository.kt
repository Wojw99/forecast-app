package com.example.forecastapp.model.repository

import android.provider.SyncStateContract
import android.util.Log
import com.example.forecastapp.Constants
import com.example.forecastapp.model.Welcome
import com.example.forecastapp.model.api.RetrofitInstance
import retrofit2.Response

class ForecastRepository {
    suspend fun getOneCallForecast(lat: Double, lon: Double,
                           exclude: String = Constants.DEFAULT_PARAMS,
                           apiKey: String = Constants.API_KEY) : Response<Welcome> {
        Log.d("ForecastRepository", "Getting new forecast")
        return RetrofitInstance.api.getOneCallForecast(lat, lon, exclude, apiKey)
    }
}