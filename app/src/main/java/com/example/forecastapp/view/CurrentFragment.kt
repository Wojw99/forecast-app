package com.example.forecastapp.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.forecastapp.Constants
import com.example.forecastapp.databinding.FragmentCurrentBinding
import com.example.forecastapp.model.Welcome
import com.example.forecastapp.viewmodel.CurrentViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class CurrentFragment : Fragment() {
    private lateinit var currentViewModel: CurrentViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

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

        currentViewModel = ViewModelProvider(this).get(CurrentViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        //getForecastForLastLocation()
        getForecastAndUpdateView()

//        binding.buttonOk.setOnClickListener {
//            lifecycleScope.launch {
//                applyAnimations()
//            }
//        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getForecastForLastLocation(){
        val accessFineLocation = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        val accessCoarseLocation = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
        if (accessFineLocation != PackageManager.PERMISSION_GRANTED && accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if(location != null){
                Constants.currentLat = location.latitude
                Constants.currentLon = location.longitude
                getForecastAndUpdateView()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getForecastAndUpdateView(){
        currentViewModel.getOneCallForecast(Constants.currentLat, Constants.currentLon)
        currentViewModel.forecastResponse.observe(viewLifecycleOwner, Observer { response ->
            if(response.isSuccessful){
                Log.d("Response", response.body()!!.current.weather[0].description)
                updateView(response.body()!!)
            }
            else{
                // TODO: Create custom alert box and show here an error message
                Log.d("Response", response.errorBody().toString())
                Log.d("ErrorCode:", response.code().toString())
            }
        })
    }

//    private suspend fun applyAnimations(){
//        binding.buttonOk.animate().alpha(0f).duration = 500L
//        delay(500)
//        binding.buttonOk.animate().alpha(1f).duration = 500L
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateView(welcome: Welcome){
        val directionX = if (welcome.lon > 0) "E" else "W"
        val directionY = if (welcome.lat > 0) "N" else "S"
        val coords = "${welcome.lat} $directionY, ${welcome.lon} $directionX"
        binding.tvCoord.text = coords
        binding.tvCity.text = welcome.timezone

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