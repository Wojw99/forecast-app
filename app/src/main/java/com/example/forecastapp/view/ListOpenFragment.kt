package com.example.forecastapp.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
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
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.round
import kotlin.math.roundToInt

class ListOpenFragment : Fragment() {
    private var _binding: FragmentListOpenBinding? = null
    private val binding get() = _binding!!

    private lateinit var histDailyViewModel: HistDailyViewModel

    private var histDaily: HistDaily? = null
    private var checkedHistDaily: CheckedHistDaily? = null

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

    /**
     * Updates the left side of the screen (with a forecast weather)
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateHistDailyView(histDaily: HistDaily?){
        if(histDaily == null) return

        val directionX = if (histDaily.lon > 0) "E" else "W"
        val directionY = if (histDaily.lat > 0) "N" else "S"
        val coords = "${histDaily.lat} $directionY, ${histDaily.lon} $directionX"
        binding.tvCoord.text = coords

        binding.tvCity.text = histDaily.city

        val accuracy = "Poprawność: ${histDaily.accuracy}%"
        binding.tvAccuracy.text = accuracy

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        binding.tvDay.text = histDaily.forecastDate.format(formatter)

        val tempValue = (histDaily.temp - 273.15).roundToInt()
        val temp = "${tempValue}°C"
        binding.tvTempValueL.text = temp

        val clouds = "${histDaily.clouds}%"
        binding.tvCloudsValueL.text = clouds

        val humidity = "${histDaily.humidity}%"
        binding.tvHumidityValueL.text = humidity

        val windDegree = "${histDaily.wind_deg}°"
        binding.tvWindDegreeValueL.text = windDegree

        val windSpeed = "${histDaily.wind_speed} km/h"
        binding.tvWindSpeedValueL.text = windSpeed

        val pressure = "${histDaily.pressure} hPa"
        binding.tvPressureValueL.text = pressure

        binding.tvUvIndexValueL.text = histDaily.uvi.toString()
    }

    /**
     * Updates the right side of the screen (with a real weather)
     * */
    private fun updateCheckedHistDailyView(checkedHistDaily: CheckedHistDaily?){
        if(checkedHistDaily == null) return

        val tempValue = (checkedHistDaily.temp - 273.15).roundToInt()
        val temp = "${tempValue}°C"
        binding.tvTempValueR.text = temp

        val clouds = "${checkedHistDaily.clouds}%"
        binding.tvCloudsValueR.text = clouds

        val humidity = "${checkedHistDaily.humidity}%"
        binding.tvHumidityValueR.text = humidity

        val windDegree = "${checkedHistDaily.wind_deg}°"
        binding.tvWindDegreeValueR.text = windDegree

        val windSpeed = "${checkedHistDaily.wind_speed} km/h"
        binding.tvWindSpeedValueR.text = windSpeed

        val pressure = "${checkedHistDaily.pressure} hPa"
        binding.tvPressureValueR.text = pressure

        binding.tvUvIndexValueR.text = checkedHistDaily.uvi.toString()
    }

    private fun updateDataDifference(histDaily: HistDaily?, checkedHistDaily: CheckedHistDaily?){
        if (histDaily == null || checkedHistDaily == null){
            return
        }
        calculateDiff(histDaily.temp.roundToInt(), checkedHistDaily.temp.roundToInt(), binding.tvTempValueC)
        calculateDiff(histDaily.clouds, checkedHistDaily.clouds, binding.tvCloudsValueC)
        calculateDiff(histDaily.humidity, checkedHistDaily.humidity, binding.tvHumidityValueC)
        calculateDiff(histDaily.pressure, checkedHistDaily.pressure, binding.tvPressureValueC)
        calculateDiff(histDaily.wind_deg, checkedHistDaily.wind_deg, binding.tvWindDegreeValueC)
        calculateDiff(histDaily.wind_speed, checkedHistDaily.wind_speed, binding.tvWindSpeedValueC)
        calculateDiff(histDaily.uvi, checkedHistDaily.uvi, binding.tvUvIndexValueC)
    }

    /**
     * Calculate difference of two values and updates values TextView with new color and content
     * */
    private fun calculateDiff(val1: Double, val2: Double, textView: TextView){
        val diff = val2 - val1
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        if (diff > 0){
            val diffStr = "+${df.format(diff)}"
            textView.text = diffStr
            textView.setTextColor(Color.LTGRAY)
        } else if (diff < 0){
            textView.text = df.format(diff)
            textView.setTextColor(Color.LTGRAY)
        } else {
            textView.text = df.format(diff)
            textView.setTextColor(Color.GREEN)
        }
    }

    /**
     * Calculate difference of two values and updates values TextView with new color and content
     * */
    private fun calculateDiff(val1: Int, val2: Int, textView: TextView){
        calculateDiff(val1.toDouble(), val2.toDouble(), textView)
    }

    /**
     * Sets up view model observer and loads new values
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setup(){
        histDailyViewModel.histDaily.observe(viewLifecycleOwner, Observer { daily ->
            histDaily = daily
            updateHistDailyView(daily)
            updateDataDifference(histDaily, checkedHistDaily)
        })

        histDailyViewModel.checkedHistDaily.observe(viewLifecycleOwner, Observer { daily ->
            checkedHistDaily = daily
            updateCheckedHistDailyView(daily)
            updateDataDifference(histDaily, checkedHistDaily)
        })

        histDailyViewModel.readCheckedHistDailyById(currentId)
        histDailyViewModel.readHistDailyById(currentId)
    }
}