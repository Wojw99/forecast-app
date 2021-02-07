package com.example.forecastapp.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastapp.R
import com.example.forecastapp.databinding.FragmentCurrentBinding
import com.example.forecastapp.databinding.FragmentForecastBinding
import com.example.forecastapp.databinding.FragmentListOpenBinding
import com.example.forecastapp.model.CheckedHistDaily
import com.example.forecastapp.model.HistDaily
import com.example.forecastapp.viewmodel.HistDailyViewModel
import com.example.forecastapp.viewmodel.HistDailyViewModel.Companion.currentId
import kotlinx.android.synthetic.main.fragment_forecast.*
import kotlinx.android.synthetic.main.fragment_list_open.*
import kotlinx.android.synthetic.main.fragment_list_open.view.*
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt


class ListOpenFragment : Fragment() {

    private var _binding: FragmentListOpenBinding? = null
    private val binding get() = _binding!!

  private  lateinit var histDailyViewModel: HistDailyViewModel


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentListOpenBinding.inflate(inflater, container, false)
        val view = binding.root

        histDailyViewModel = ViewModelProvider(requireActivity()).get(HistDailyViewModel::class.java)
        setup()





      return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateHistDailyView(histDaily: HistDaily){
        // Tu musi zostać rozpisane aktulizowanie się widoku pogody prognozowanej
        Log.d("Update", "id: ${histDaily.id}, city: ${histDaily.city}, temp: ${histDaily.temp}, accuracy: ${histDaily.accuracy}, hum:${histDaily.humidity}")

        var city = histDaily.city
        binding.tvCityopen.text = city

        val directionX = if (histDaily.lon > 0) "E" else "W"
        val directionY = if (histDaily.lat > 0) "N" else "S"
        val geoStr = "${histDaily.lat} $directionY, ${histDaily.lon} $directionX"

        binding.tvcoordopen.text = geoStr

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formattedDate = histDaily.forecastDate.format(formatter)
        binding.tvdataopen.text=formattedDate


        var accuracy = histDaily.accuracy.toDouble()*100
        var ac = "${accuracy}%"
        binding.tvcorrectopen.text = ac

         var temp = (histDaily.temp -273.15).roundToInt()
        var tempprob = "${temp}°C"
        binding.tvtempprob.text=tempprob


        var hum = histDaily.humidity
        var humidityprob = "${hum}%"
        binding.tvhumidityprob.text=humidityprob

        var press = histDaily.pressure
        var pressureprob = "${press} hPa"
        binding.tvpressureprob.text = pressureprob

        var windsp = histDaily.wind_speed
        var windspeedprob = "${windsp} km/h"
        binding.tvwindspeedprob.text = windspeedprob

        var winddirect = histDaily.wind_deg
        var directionwindprob = "${winddirect}°"
        binding.tvdirectionwindprob.text = directionwindprob

        var cloudns = histDaily.clouds
        var cloudnessprob = "${cloudns}%"
        binding.tvcloudnessprob.text = cloudnessprob

        var UV = histDaily.uvi
        var uvprob = "${UV}"
        binding.tvUVprob.text = uvprob

    }



    private fun updateCheckedHistDailyView(checkedHistDaily: CheckedHistDaily){
        // Tu musi zostać rozpisane aktulizowanie się widoku pogody rzeczywistej
        Log.d("Update", "id: ${checkedHistDaily.id}, temp: ${checkedHistDaily.temp}, hum:${checkedHistDaily.humidity}")

        var tempreal = (checkedHistDaily.temp - 273.15).roundToInt()
        var tempreal2 = "${tempreal}°C"
        binding.tvtemprel.text=tempreal2

        var pressure = checkedHistDaily.pressure
        var pressurereal = "${pressure} hPa"
        binding.tvpressurerel.text = pressurereal

        var humidity = checkedHistDaily.humidity
        var humidityreal = "${humidity}%"
        binding.tvhumidityrel.text=humidityreal

        var windspeed=checkedHistDaily.wind_speed
        var windspeedreal ="${windspeed} km/h"
        binding.tvwindspeedrel.text=windspeedreal

        var directionwind = checkedHistDaily.wind_deg
        var directionwindreal = "${directionwind}°"
        binding.tvdirectionwindrel.text = directionwindreal

        var cloudness = checkedHistDaily.clouds
        var cloudnessreal = "${cloudness}%"
        binding.tvcloudnessrel.text=cloudnessreal

        var uv = checkedHistDaily.uvi
        var uvreal = "${uv}"
        binding.tvUVrel.text = uvreal



    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setup(){
        histDailyViewModel.histDaily.observe(viewLifecycleOwner, Observer { daily ->
            updateHistDailyView(daily)
        })

        histDailyViewModel.checkedHistDaily.observe(viewLifecycleOwner, Observer { daily ->
            updateCheckedHistDailyView(daily)
        })

        histDailyViewModel.readCheckedHistDailyById(HistDailyViewModel.currentId)
        histDailyViewModel.readHistDailyById(HistDailyViewModel.currentId)


    }




}