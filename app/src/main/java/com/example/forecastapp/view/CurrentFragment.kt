package com.example.forecastapp.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.forecastapp.R
import com.example.forecastapp.databinding.FragmentCurrentBinding
import com.example.forecastapp.model.welcome.Welcome
import com.example.forecastapp.viewmodel.ForecastViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class CurrentFragment : Fragment() {
    private lateinit var forecastViewModel: ForecastViewModel

    private var _binding: FragmentCurrentBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCurrentBinding.inflate(inflater, container, false)
        val view = binding.root

        forecastViewModel = ViewModelProvider(this).get(ForecastViewModel::class.java)
        getForecastAndUpdateView()

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
                forecastViewModel.getOneCallForecast(ForecastViewModel.defaultLat, ForecastViewModel.defaultLon)
            else
                forecastViewModel.getOneCallForecast(ForecastViewModel.userCity)

        } catch(ex: Exception){
            Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
        }
        forecastViewModel.forecastBody.observe(viewLifecycleOwner, Observer { forecast ->
            updateView(forecast)
        })
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
                    Toast.makeText(requireContext(),getText(R.string.field_empty),Toast.LENGTH_SHORT).show()
                }
            }
            dialog.setNegativeButton(getText(R.string.cancel)) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.cancel()
            }

            dialog.show()
        }
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
     * Update view data with current weather for selected day
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateView(welcome: Welcome){
        // Todo: weather icon changing
        val directionX = if (welcome.lon > 0) "E" else "W"
        val directionY = if (welcome.lat > 0) "N" else "S"
        val coords = "${welcome.lat} $directionY, ${welcome.lon} $directionX"
        binding.tvCoord.text = coords
        binding.tvCity.text = forecastViewModel.forecastCity

        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        binding.tvDay.text = currentDate.format(formatter)

        val current = welcome.current

        updateIcon(current.weather[0].icon)

        val tempValue = (current.temp - 273.15).roundToInt()
        val temp = "${tempValue}°C"
        binding.tvTemp.text = temp

        val clouds = "${current.clouds}%"
        binding.tvCloudsValue.text = clouds

        val humidity = "${current.humidity}%"
        binding.tvHumidityValue.text = humidity

        val windDegree = "${current.windDeg}°"
        binding.tvWindDegreevalue.text = windDegree

        val windSpeed = "${current.windSpeed} km/h"
        binding.tvWindSpeedValue.text = windSpeed

        val pressure = "${current.pressure} hPa"
        binding.tvPressureValue.text = pressure

        binding.tvUvIndexValue.text = current.uvi.toString()
    }

    // Todo: repair this function
    private fun formatCoord(coord: Double): String{
        var str = coord.toString().replace(".","")

        val leftLeft = str.substring(0, 2)
        val left = str.substring(2, 4)
        val right = str.substring(4, 6)

        str = ("${leftLeft}° ${left}' ${right}\" ")

        return str
    }
}