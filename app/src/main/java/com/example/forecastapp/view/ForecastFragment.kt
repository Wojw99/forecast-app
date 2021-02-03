package com.example.forecastapp.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.forecastapp.R
import com.example.forecastapp.databinding.FragmentForecastBinding
import com.example.forecastapp.model.welcome.Welcome
import com.example.forecastapp.viewmodel.ForecastViewModel
import kotlinx.coroutines.channels.consumesAll
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class ForecastFragment : Fragment() {
    // visible temperature value (0 - morn, 1 - day, 2 - eve, 3 - night)
    private var temperatureIndex = 0

    // day of week for which is the actual forecast (0 - tomorrow, 1 - day after tomorrow, etc.)
    private var dayIndex = 0

    // view model
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
        setupNextButtons()
        setupChangeButton()
        setupFloatingButton()

        return view
    }

//    private fun showCustomToast(message: String, viewGroup: ViewGroup, duration: Int){
//        val layout = layoutInflater.inflate(R.layout.toast_layout, viewGroup)
//        layout.findViewById<TextView>(R.id.tvContent).text = message
//
//        val toast = Toast(activity)
//        toast.setGravity(Gravity.CENTER, 0, 0)
//        toast.duration = duration
//        toast.view = layout
//
//        toast.show()
//    }

    /**
     * Gets a forecast parent class (welcome) from view model
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getForecastAndUpdateView(){
        try{
            if(ForecastViewModel.userCity.isEmpty())
                forecastViewModel.getOneCallForecast(ForecastViewModel.defaultLat, ForecastViewModel.defaultLon)
            else
                forecastViewModel.getOneCallForecast(ForecastViewModel.userCity)
        } catch(ex: IOException){
            Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
        }
        forecastViewModel.forecastBody.observe(viewLifecycleOwner, Observer { forecast ->
            updateView(forecast, dayIndex)
        })
    }

    /**
     * Setup saving current showed forecast to the database
     * */
    private fun setupFloatingButton(){
        binding.fabSaving.setOnClickListener {
            lifecycleScope.launch {
                AnimationHelper.clickAnimate(view = binding.fabSaving, scaleMin = 0.8f)
            }
        }
    }

    /**
     * Setup changing a forecast city by the user
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
                    forecastViewModel.getOneCallForecast(text)
                }
                else{
                    dialogInterface.cancel()
                    Toast.makeText(requireContext(),getText(R.string.field_empty),Toast.LENGTH_SHORT)
                            .apply { setGravity(Gravity.CENTER,0,0) }
                            .show()
                }
            }
            dialog.setNegativeButton(getText(R.string.cancel)) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.cancel()
            }

            dialog.show()
        }
    }

    /**
     * Setup left and right arrow buttons
     * */
    private fun setupNextButtons(){
        binding.buttonLeft.setOnClickListener {
            lifecycleScope.launch {
                AnimationHelper.clickAnimate(view = binding.buttonLeft, scaleMin = 0.8f, milis = 150L)
            }
            temperatureIndex -= 1
            updateTemperature()
        }
        binding.buttonRight.setOnClickListener {
            lifecycleScope.launch {
                AnimationHelper.clickAnimate(view = binding.buttonRight, scaleMin = 0.8f, milis = 150L)
            }
            temperatureIndex += 1
            updateTemperature()
        }
    }

    /**
     * Updates visible temperature value
     * @param offset default values is 273.15 which is kelvin to celsius offset
     * */
    private fun updateTemperature(offset: Double = 273.15){
        temperatureIndex = normalizeTempIndex(temperatureIndex)
        if (forecastViewModel.forecastBody.value != null){
            val temp = forecastViewModel.forecastBody.value!!.daily[dayIndex].temp
            var tempValue = 0

            binding.tvTemp.alpha = 0f
            binding.tvTempDesc.alpha = 0f

            if (temperatureIndex == 0){
                tempValue = (temp.morn - offset).roundToInt()
                binding.tvTempDesc.text = getText(R.string.morn)
            } else if (temperatureIndex == 1){
                tempValue = (temp.day - offset).roundToInt()
                binding.tvTempDesc.text = getText(R.string.day)
            } else if (temperatureIndex == 2){
                tempValue = (temp.eve - offset).roundToInt()
                binding.tvTempDesc.text = getText(R.string.eve)
            } else if (temperatureIndex == 3){
                tempValue = (temp.night - offset).roundToInt()
                binding.tvTempDesc.text = getText(R.string.night)
            }

            val tempStr = "${tempValue}°C"
            binding.tvTemp.text = tempStr

            lifecycleScope.launch {
                AnimationHelper.fadeOutAnimate(binding.tvTemp)
                AnimationHelper.fadeOutAnimate(binding.tvTempDesc)
            }
        }
    }

    private fun normalizeTempIndex(temp: Int) : Int{
        if (temp < 0) return 3
        else if (temp > 3) return 0
        return temp
    }

    /**
     * Setup spinner control with adapter and list of next seven days
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupAdapter(){
        val list = getWeekList()
        val adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, list)
        binding.spinnerDays.adapter = adapter
        binding.spinnerDays.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(forecastViewModel.forecastBody.value != null && position < 7 && position >= 0) {
                    dayIndex = position
                    updateView(forecastViewModel.forecastBody.value!!, dayIndex)
                }
            }
        }
    }

    /**
     * Creates a list of seven next days (string values)
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getWeekList() : MutableList<String>{
        val list = mutableListOf(getText(R.string.tomorrow).toString(), getText(R.string.tomorrowPlus).toString())

        var currentDay = LocalDateTime.now().plusDays(2)
        for(i in 1..5){
            currentDay = currentDay.plusDays(1)
            val newDay = "${currentDay.dayOfMonth}.${currentDay.month.value}"
            list.add(newDay)
        }

        return list
    }

    /**
     * Update view data with selected day info
     * @param dayOfWeek selected forecast day (0 - tomorrow, 1 - day after tomorrow, etc.)
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateView(welcome: Welcome, dayOfWeek: Int){
        // Todo: weather icon changing
        val directionX = if (welcome.lon > 0) "E" else "W"
        val directionY = if (welcome.lat > 0) "N" else "S"
        val coords = "${welcome.lat} $directionY, ${welcome.lon} $directionX"
        binding.tvCoord.text = coords
        binding.tvCity.text = forecastViewModel.forecastCity

        val days = 1 + dayOfWeek
        val forecastDate = LocalDateTime.now().plusDays(days.toLong())
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        binding.tvDay.text = forecastDate.format(formatter)

        val daily = welcome.daily[dayOfWeek]

        updateTemperature()

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