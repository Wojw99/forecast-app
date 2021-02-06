package com.example.forecastapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastapp.R
import com.example.forecastapp.databinding.FragmentCurrentBinding
import com.example.forecastapp.databinding.FragmentForecastBinding
import com.example.forecastapp.databinding.FragmentListOpenBinding
import com.example.forecastapp.model.HistDaily
import com.example.forecastapp.viewmodel.HistDailyViewModel
import kotlin.math.roundToInt


class ListOpenFragment : Fragment() {

    //private var historicalDailyWeatherModel1 = "pogoda1"
   // private var historicalDailyWeatherModel2 = "pogoda2"

    //private var hdweathermodel:

    private var _binding: FragmentListOpenBinding? = null

    private val binding get() = _binding!!

    lateinit var adapterlist: ListAdapter
    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var histviewmodel:HistDailyViewModel


   // var elementlista =adapterlist.forecastList[0]

    //var lista=adapterlist.setData()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentListOpenBinding.inflate(inflater, container, false)
        val view = binding.root

        //return inflater.inflate(R.layout.fragment_list_open, container, false)
        //val directionX = if (elementlista.lon > 0) "E" else "W"
        //val directionY = if (elementlista.lat > 0) "N" else "S"
        //val coords = "${elementlista.lat} $directionY, ${elementlista.lon} $directionX"
       // val tempValue = (elementlista.temp - 273.15).roundToInt()
       // val temp = "${tempValue}°C"
        //binding.tvcoordopen.text=coords


        //probabilty parameters


        var temp = 70.0
        var tempprob = "${temp}°C"
        binding.tvtempprob.text=tempprob

        var press = 1019
        var pressureprob = "${press} hPa"
        binding.tvpressureprob.text = pressureprob

        var hum = 34
        var humidityprob = "${hum}%"
        binding.tvhumidityprob.text=humidityprob

        var windsp = 1.22
        var windspeedprob = "${windsp} km/h"
        binding.tvwindspeedprob.text = windspeedprob

        var winddirect = 15.5
        var directionwindprob = "${winddirect}°"
        binding.tvdirectionwindprob.text = directionwindprob

        var cloudns = 0.0
        var cloudnessprob = "${cloudns}%"
        binding.tvcloudnessprob.text = cloudnessprob

        var UV = 2.99
        var uvprob = "${UV}"
        binding.tvUVprob.text = uvprob



        //real parameters

        var tempreal = 60.0
        var tempreal2 = "${tempreal}°C"
        binding.tvtemprel.text=tempreal2

        var pressure = 1030
        var pressurereal = "${pressure} hPa"
        binding.tvpressurerel.text = pressurereal

        var humidity = 34
        var humidityreal = "${humidity}%"
        binding.tvhumidityrel.text=humidityreal

        var windspeed=1.20
        var windspeedreal ="${windspeed} km/h"
        binding.tvwindspeedrel.text=windspeedreal

        var directionwind = 13.0
        var directionwindreal = "${directionwind}°"
        binding.tvdirectionwindrel.text = directionwindreal

        var cloudness = 0.0
        var cloudnessreal = "${cloudness}%"
        binding.tvcloudnessrel.text=cloudnessreal

        var uv = 3.0
        var uvreal = "${uv}"
        binding.tvUVrel.text = uvreal


        //difference value

        //var tempdiff = binding.tvtempdif.text
        var pressurediff=binding.tvpressuredif.text
        var humiditydiff = binding.tvhumiditydif.text
        var windspeeddiff = binding.tvwindspeeddif.text
        var winddirectdiff = binding.tvwinddirectdiff.text
        var cloudnessdiff = binding.tvcloudnessdif.text
        var uvdiff = binding.tvUVdif.text



       // if(temp > temperature)
       // {

        var x = tempreal-temp

        if(tempreal> temp)
        {
            var tempdiff = "+${x}"
            binding.tvtempdif.text = tempdiff
        }
        else if (temp>tempreal)
        {
            var tempdiff = "${x}"
            binding.tvtempdif.text = tempdiff
        }
        else
        {
            var tempdiff = "0"
            binding.tvtempdif.text = tempdiff
        }







       // }
//        else if (temperature < temp)
//        {
//            var x = temp.toDouble()-temperature.toDouble()
//            var tempdiff = "+${x}"
//            binding.tvtempdif.text = tempdiff
//        }
//        else(temperature==temp)
//        {
//
//            var tempdiff = 0
//            binding.tvtempdif.text = tempdiff.toString()
//        }




      return view

    }



}