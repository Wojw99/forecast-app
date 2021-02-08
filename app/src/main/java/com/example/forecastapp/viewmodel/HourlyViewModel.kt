package com.example.forecastapp.viewmodel

import android.app.Application
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forecastapp.model.HistDaily
import com.example.forecastapp.model.data.MyDatabase
import com.example.forecastapp.model.welcome.Welcome
import com.example.forecastapp.model.repository.ForecastRepository
import com.example.forecastapp.model.repository.HistDailyRepository
import com.example.forecastapp.model.welcome.Daily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HourlyViewModel(application: Application) : AndroidViewModel(application) {
    val forecastBody: MutableLiveData<Welcome> = MutableLiveData()
    var forecastCity: String = ""
    private val forecastRepository = ForecastRepository()

    /**
     * Firstly, calls API for a geocoding object (cityName as argument) and gets coords from it.
     * Next, calls API for a 7-day forecast (lat and lon as arguments) and changes forecastBody
     * */
    fun getOneCallForecast(cityName: String) {
        viewModelScope.launch {
            // Todo: When lat and lon is same as previous then method calls localRepository with saved forecast
            Log.d("ForecastViewModel","I have city name: $cityName")

            // Getting new coords for given city name
            val geocodeResponse = forecastRepository.getGeocoding(cityName)

            if(geocodeResponse.isSuccessful){
                forecastCity = geocodeResponse.body()!!.name
            }
            else{
                Log.d("Hourly", geocodeResponse.errorBody().toString())
                Log.d("Hourly", geocodeResponse.code().toString())
                return@launch
            }

            val coords = geocodeResponse.body()!!.coord
            Log.d("Hourly","I have coords: ${coords.lat}, ${coords.lon}")

            // Getting new forecast for given coords
            val response = forecastRepository.getOneCallForecast(coords.lat, coords.lon, "minutely")
            Log.d("Hourly","I have a forecast: ${response.body()!!.current.weather[0].description}")

            if(response.isSuccessful){
                forecastBody.value = response.body()!!
            }
            else{
                Log.d("Hourly", response.errorBody().toString())
                Log.d("Hourly", response.code().toString())
                return@launch
            }

            userCity = cityName
        }
    }

    /**
     * Calls API for a 7-day forecast (lat and lon as arguments) and changes forecastBody. It
     * is faster than getOneCallForecast(cityName: String) because calls the API one time.
     * */
    fun getOneCallForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            // Todo: When lat and lon is same as previous then method calls localRepository with saved forecast
            val response = forecastRepository.getOneCallForecast(lat, lon, "minutely")

            if(response.isSuccessful){
                forecastCity = response.body()!!.timezone
                forecastBody.value = response.body()!!
                Log.d("Response", response.body()!!.current.weather[0].description)
            }
            else{
                Log.d("Response", response.errorBody().toString())
                Log.d("ErrorCode:", response.code().toString())
            }
        }
    }

    companion object{
        var defaultLat = 50.2584
        var defaultLon = 19.0275
        var userCity = ""
    }
}