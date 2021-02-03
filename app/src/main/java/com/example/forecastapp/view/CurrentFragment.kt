package com.example.forecastapp.view

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.forecastapp.Constants
import com.example.forecastapp.databinding.FragmentCurrentBinding
import com.example.forecastapp.model.Welcome
import com.example.forecastapp.viewmodel.CurrentViewModel
import com.example.forecastapp.viewmodel.ForecastViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
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
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle("Change your city:")

            val editText = EditText(requireContext())
            editText.inputType = InputType.TYPE_CLASS_TEXT
            dialog.setView(editText)

            dialog.setPositiveButton("Ok") { dialogInterface: DialogInterface, _: Int ->
                val text = editText.text.toString()
                if(text.isNotEmpty()){
                    forecastViewModel.getOneCallForecast(text)
                }
                else{
                    dialogInterface.cancel()
                    Toast.makeText(requireContext(),"The field was empty!",Toast.LENGTH_SHORT).show()
                }
            }
            dialog.setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.cancel()
            }

            dialog.show()
        }
    }

//    private suspend fun applyAnimations(){
//        binding.buttonOk.animate().alpha(0f).duration = 500L
//        delay(500)
//        binding.buttonOk.animate().alpha(1f).duration = 500L
//    }

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