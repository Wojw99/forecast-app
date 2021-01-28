package com.example.forecastapp.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.forecastapp.Constants
import com.example.forecastapp.R
import com.example.forecastapp.databinding.FragmentForecastBinding
import com.example.forecastapp.model.Welcome
import com.example.forecastapp.viewmodel.ForecastViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class ForecastFragment : Fragment() {
    private lateinit var forecastViewModel: ForecastViewModel

    private var _binding: FragmentForecastBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        val view = binding.root

        forecastViewModel = ViewModelProvider(this).get(ForecastViewModel::class.java)
        getForecastAndUpdateView()
        setupAdapter()

        return view
    }

    private fun setupAdapter(){
        val list = mutableListOf("Jutro","Pojutrze","28.01")
        val adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, list)
        binding.spinnerDays.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getForecastAndUpdateView(){
        forecastViewModel.getOneCallForecast(Constants.currentLat, Constants.currentLon)
        forecastViewModel.forecastResponse.observe(viewLifecycleOwner, Observer { response ->
            if(response.isSuccessful){
                Log.d("Response", response.body()!!.current.weather[0].description)
                // TODO: Day selecting by the user
                updateView(response.body()!!, 0)
            }
            else{
                // TODO: Create custom alert box and show an error message here
                Log.d("Response", response.errorBody().toString())
                Log.d("ErrorCode:", response.code().toString())
            }
        })
    }

    /**
     * Update view data with selected day info
     * @param dayOfWeek selected forecast day (0 - tomorrow, 1 - day after tomorrow, etc.)
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateView(welcome: Welcome, dayOfWeek: Int){
        val directionX = if (welcome.lon > 0) "E" else "W"
        val directionY = if (welcome.lat > 0) "N" else "S"
        val coords = "${welcome.lat} $directionY, ${welcome.lon} $directionX"
        binding.tvCoord.text = coords
        binding.tvCity.text = welcome.timezone

        val days = 1 + dayOfWeek
        val forecastDate = LocalDateTime.now().plusDays(days.toLong())
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        binding.tvDay.text = forecastDate.format(formatter)

        val daily = welcome.daily[dayOfWeek]

        val tempValue = (daily.temp.day - 273.15).roundToInt()
        val temp = "${tempValue}°C"
        binding.tvTemp.text = temp

        val clouds = "${daily.clouds}%"
        binding.tvCloudsValue.text = clouds

        val humidity = "${daily.humidity}%"
        binding.tvHumidityValue.text = humidity

        val windDegree = "${daily.windDeg}°"
        binding.tvWindDegreevalue.text = windDegree

        val windSpeed = "${daily.windSpeed} km/h"
        binding.tvWindSpeedValue.text = windSpeed

        val pressure = "${daily.pressure} hPa"
        binding.tvPressureValue.text = pressure

        binding.tvUvIndexValue.text = daily.uvi.toString()
    }
}