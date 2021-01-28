package com.example.forecastapp.model.api

import com.example.forecastapp.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: OpenWeatherApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // Retrofit must know how serialize the data
        .build()
        .create(OpenWeatherApi::class.java)
}