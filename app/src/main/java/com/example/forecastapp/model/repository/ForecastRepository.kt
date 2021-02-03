package com.example.forecastapp.model.repository

import android.util.Log
import com.example.forecastapp.Constants
import com.example.forecastapp.model.welcome.Welcome
import com.example.forecastapp.model.api.RetrofitInstance
import com.example.forecastapp.model.geocoding.Geocoding
import retrofit2.Response

class ForecastRepository {
    suspend fun getOneCallForecast(lat: Double, lon: Double,
                           exclude: String = Constants.DEFAULT_PARAMS,
                           apiKey: String = Constants.API_KEY) : Response<Welcome> {
        Log.d("ForecastRepository", "Getting new forecast")
        return RetrofitInstance.api.getOneCallForecast(lat, lon, exclude, apiKey)
    }

    suspend fun getGeocoding(cityName: String, apiKey: String = Constants.API_KEY) : Response<Geocoding> {
        Log.d("ForecastRepository", "Getting new geocoding")
        return RetrofitInstance.api.getGeocoding(cityName, apiKey)
    }
}