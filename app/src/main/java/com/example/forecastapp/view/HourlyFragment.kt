package com.example.forecastapp.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.forecastapp.R
import com.example.forecastapp.databinding.FragmentHourlyBinding
import com.example.forecastapp.model.welcome.Welcome
import com.example.forecastapp.viewmodel.ForecastViewModel
import com.example.forecastapp.viewmodel.HourlyViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import kotlin.time.hours

class HourlyFragment : Fragment() {
    private var hourlIndex = 0
    private lateinit var hourlyViewModel: HourlyViewModel

    private var _binding: FragmentHourlyBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHourlyBinding.inflate(inflater, container, false)
        val view = binding.root

        hourlyViewModel = ViewModelProvider(this).get(HourlyViewModel::class.java)

        getForecastAndUpdateView()

        setupAdapter()
        setupChangeButton()

        return view
    }

    /**
     * Gets a forecast parent class (welcome) from view model
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getForecastAndUpdateView(){
        try{
            if(ForecastViewModel.userCity.isEmpty())
                hourlyViewModel.getOneCallForecast(ForecastViewModel.defaultLat, ForecastViewModel.defaultLon)
            else
                hourlyViewModel.getOneCallForecast(ForecastViewModel.userCity)
        } catch(ex: IOException){
            Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
        }
        hourlyViewModel.forecastBody.observe(viewLifecycleOwner, Observer { forecast ->
            updateView(forecast, hourlIndex)
        })
    }


    /**
     * Shows toast message in the center of the screen
     * */
    private fun showToastCenter(text: String){
        Toast.makeText(requireContext(),text, Toast.LENGTH_SHORT)
            .apply { setGravity(Gravity.CENTER,0,0) }
            .show()
    }

    /**
     * Set up changing a forecast city by the user
     * */
    private fun setupChangeButton(){
        binding.tvCity.setOnClickListener {
            lifecycleScope.launch {
                AnimationHelper.clickAnimate(binding.tvCity)
            }

            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle(getText(R.string.city_changing))

            val editText = EditText(requireContext())
            editText.inputType = InputType.TYPE_CLASS_TEXT
            dialog.setView(editText)

            dialog.setPositiveButton(getText(R.string.ok)) { dialogInterface: DialogInterface, _: Int ->
                val text = editText.text.toString()
                if(text.isNotEmpty()){
                    hourlyViewModel.getOneCallForecast(text)
                }
                else{
                    dialogInterface.cancel()
                    showToastCenter(getString(R.string.field_empty))
                }
            }
            dialog.setNegativeButton(getText(R.string.cancel)) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.cancel()
            }

            dialog.show()
        }
    }

    /**
     * Set up spinner control with adapter and list of next seven days
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupAdapter(){
        val list = getList()
        val adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, list)
        binding.spinnerDays.adapter = adapter
        binding.spinnerDays.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(hourlyViewModel.forecastBody.value != null && position < 48 && position >= 0) {
                    hourlIndex = position
                    updateView(hourlyViewModel.forecastBody.value!!, hourlIndex)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getList() : List<String>{
        val list = mutableListOf<String>()
        var currentDate = LocalDateTime.now()

        for(i in 1..48){
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val dayF = currentDate.format(formatter)
            val hourF = "${currentDate.hour + 1}:00, $dayF"
            list.add(hourF)
            currentDate = currentDate.plusHours(1)
        }

        return list
    }

    /**
     * Updates the weather icon with respect to the icon code
     * @param iconCode icon code that goes with API data
     * */
    private fun updateIcon(iconCode: String){
        // removes "n" and "d" from code because we have only day icons
        val newIconCode = iconCode
            .replace("n","")
            .replace("d","")

        when(newIconCode){
            "01" -> binding.imageView.setImageResource(R.drawable.n_clear_sky)
            "02" -> binding.imageView.setImageResource(R.drawable.n_few_clouds)
            "03" -> binding.imageView.setImageResource(R.drawable.n_scattered_clouds)
            "04" -> binding.imageView.setImageResource(R.drawable.n_broken_clouds)
            "09" -> binding.imageView.setImageResource(R.drawable.n_shower_rain)
            "10" -> binding.imageView.setImageResource(R.drawable.n_rain)
            "11" -> binding.imageView.setImageResource(R.drawable.n_thunderstorm)
            "13" -> binding.imageView.setImageResource(R.drawable.n_snow)
            "50" -> binding.imageView.setImageResource(R.drawable.n_mist)
        }
    }

    /**
     * Update view data with selected day info
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateView(welcome: Welcome, hoursIndex: Int){
        // Todo: weather icon changing
        val directionX = if (welcome.lon > 0) "E" else "W"
        val directionY = if (welcome.lat > 0) "N" else "S"
        val coords = "${welcome.lat} $directionY, ${welcome.lon} $directionX"
        binding.tvCoord.text = coords
        binding.tvCity.text = hourlyViewModel.forecastCity

        val hours = 1 + hoursIndex
        binding.tvDay.text = "${hours}h"

        val hourly = welcome.hourly[hoursIndex]

        updateIcon(hourly.weather[0].icon)

        val temp = "${(hourly.temp - 276.15).roundToInt()}°C"
        binding.tvTemp.text = temp

        val clouds = "${hourly.clouds}%"
        binding.tvCloudsValue.text = clouds

        val humidity = "${hourly.humidity}%"
        binding.tvHumidityValue.text = humidity

        val windDegree = "${hourly.windDeg}°"
        binding.tvWindDegreevalue.text = windDegree

        val windSpeed = "${hourly.windSpeed} km/h"
        binding.tvWindSpeedValue.text = windSpeed

        val pressure = "${hourly.pressure} hPa"
        binding.tvPressureValue.text = pressure

        binding.tvUvIndexValue.text = hourly.uvi.toString()
    }
}