package com.example.forecastapp.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
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
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.forecastapp.R
import com.example.forecastapp.databinding.FragmentForecastBinding
import com.example.forecastapp.model.CompareForecast
import com.example.forecastapp.model.HistDaily
import com.example.forecastapp.model.welcome.Welcome
import com.example.forecastapp.viewmodel.ForecastViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
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
     * Set up saving current showed forecast to the database
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupFloatingButton(){
        binding.fabSaving.setOnClickListener {
            if(forecastViewModel.forecastBody.value != null){

                val welcome = forecastViewModel.forecastBody.value!!
                val daily = welcome.daily[dayIndex]

                val city = if (forecastViewModel.forecastCity.isEmpty()) welcome.timezone else forecastViewModel.forecastCity
                val forecastDay = LocalDate.now().plusDays((dayIndex + 1).toLong())
                val todayDay = LocalDate.now()

                val histDaily = HistDaily( 0, daily.dt, forecastDay, todayDay,
                        city, welcome.lat, welcome.lon, "?", daily.sunrise, daily.sunset,
                        getTemperature(), getFeelsLike(), daily.pressure, daily.humidity,
                        daily.dewPoint, daily.windSpeed, daily.windDeg, daily.weather[0].description,
                        daily.clouds, daily.pop, daily.uvi, false)
                forecastViewModel.addToHistory(histDaily)

                Log.d("Compare (interval):", getHoursInterval().toString())

                compareForecastRequest(histDaily.dt, histDaily.temp)
                showToastCenter(getString(R.string.forecast_added))
            }
            lifecycleScope.launch {
                AnimationHelper.clickAnimate(view = binding.fabSaving, scaleMin = 0.8f)
            }
        }
    }

    /**
     * Counts interval between today and forecast day. Returns interval in hours.
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getHoursInterval() : Long{
        val forecastDay = LocalDate.now().plusDays((dayIndex + 1).toLong())
        val todayDay = LocalDate.now()
        var plusHours = 0

        when(temperatureIndex) {
            0 -> plusHours = 6
            1 -> plusHours = 12
            2 -> plusHours = 18
            3 -> plusHours = 0
        }

        val interval = forecastDay.toEpochDay() - todayDay.toEpochDay()
        return (interval * 24) - LocalDateTime.now().hour + plusHours
    }

    private fun compareForecastRequest(dt: Int, temp: Double){
        val compareForecastRequest: WorkRequest = OneTimeWorkRequestBuilder<CompareForecast>()
                .setInitialDelay(1, TimeUnit.MINUTES)
                .addTag(dt.toString())
                .addTag(temp.toString())
                .build()
        WorkManager.getInstance(requireContext()).enqueue(compareForecastRequest)
    }

    /**
     * Shows toast message in the center of the screen
     * */
    private fun showToastCenter(text: String){
        Toast.makeText(requireContext(),text,Toast.LENGTH_SHORT)
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
                    forecastViewModel.getOneCallForecast(text)
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
     * Set up left and right arrow buttons
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
     * Returns the temperature value currently set by the user
     * */
    private fun getTemperature() : Double{
        val temp = forecastViewModel.forecastBody.value!!.daily[dayIndex].temp

        when(temperatureIndex) {
            0 -> return temp.morn
            1 -> return temp.day
            2 -> return temp.eve
            3 -> return temp.night
        }

        return temp.day
    }

    /**
     * Returns the feels like value in reference to temperature currently set by the user
     * */
    private fun getFeelsLike() : Double{
        val temp = forecastViewModel.forecastBody.value!!.daily[dayIndex].feelsLike

        when(temperatureIndex) {
            0 -> return temp.morn
            1 -> return temp.day
            2 -> return temp.eve
            3 -> return temp.night
        }

        return temp.day
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
     * Set up spinner control with adapter and list of next seven days
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
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val newDay = currentDay.format(formatter)
            list.add(newDay)
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
        updateIcon(daily.weather[0].icon)

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