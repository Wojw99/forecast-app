package com.example.forecastapp.model.api
import com.example.forecastapp.model.Welcome
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    // Example:
    // https://api.openweathermap.org/data/2.5/
    // onecall?lat=33.441792&lon=-94.037689&exclude=minutely,hourly&appid=54443f860b9257ccbab2a8815b5f3e0f
    @GET("data/2.5/onecall?")
    suspend fun getOneCallForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("appid") apiKey: String
    ): Response<Welcome>
}
