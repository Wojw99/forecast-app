package com.example.forecastapp.view

import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.forecastapp.R
import com.example.forecastapp.databinding.FragmentListOpenBinding
import com.example.forecastapp.model.CheckedHistDaily
import com.example.forecastapp.model.HistDaily
import com.example.forecastapp.viewmodel.HistDailyViewModel
import com.example.forecastapp.viewmodel.HistDailyViewModel.Companion.currentId
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter

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

        setHasOptionsMenu(true)
        setup()

      return view
    }

    /**
     * Updates the left side of the screen (with a forecast weather)
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateHistDailyView(histDaily: HistDaily?){
        if(histDaily == null) {
            return
        }

        val directionX = if (histDaily.lon > 0) "E" else "W"
        val directionY = if (histDaily.lat > 0) "N" else "S"
        val coords = "${histDaily.lat} $directionY, ${histDaily.lon} $directionX"
        binding.tvCoord.text = coords

        binding.tvCity.text = histDaily.city

        var accuracyStr = "Poprawność: ${histDaily.accuracy}"
        accuracyStr += if (histDaily.accuracy != "?") "%" else ""
        binding.tvAccuracy.text = accuracyStr

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        binding.tvDay.text = histDaily.forecastDate.format(formatter)

        binding.tvTempValueL.text = formatTemperature(histDaily.temp)

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
     * Transform temperature Fahrenheit to Celsius and format value to suitable string
     * */
    private fun formatTemperature(temp: Double) : String{
        val toCelsius = 273.15
        val decForm = DecimalFormat("#.##")
        decForm.roundingMode = RoundingMode.CEILING

        val tempValue = temp - toCelsius

        return "${decForm.format(tempValue)}°C"
    }

    /**
     * Updates the right side of the screen (with a real weather)
     * */
    private fun updateCheckedHistDailyView(checkedHistDaily: CheckedHistDaily?){
        if(checkedHistDaily == null) {
            clearCenterAndRight()
            return
        }

        binding.tvTempValueR.text = formatTemperature(checkedHistDaily.temp)

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

    /**
     * Returns center and right side of the screen to the initial state
     * */
    private fun clearCenterAndRight(){
        val qMark = "?"
        binding.tvTempValueR.text = qMark
        binding.tvCloudsValueR.text = qMark
        binding.tvHumidityValueR.text = qMark
        binding.tvWindDegreeValueR.text = qMark
        binding.tvWindSpeedValueR.text = qMark
        binding.tvPressureValueR.text = qMark
        binding.tvUvIndexValueR.text = qMark

        val empty = ""
        binding.tvTempValueC.text = empty
        binding.tvCloudsValueC.text = empty
        binding.tvHumidityValueC.text = empty
        binding.tvWindDegreeValueC.text = empty
        binding.tvWindSpeedValueC.text = empty
        binding.tvPressureValueC.text = empty
        binding.tvUvIndexValueC.text = empty
    }

    /**
     * Updates the center of the screen with calculated difference of HistDaily
     * and CheckedHistDaily
     * */
    private fun updateDataDifference(histDaily: HistDaily?, checkedHistDaily: CheckedHistDaily?){
        if (histDaily == null || checkedHistDaily == null){
            return
        }
        calculateDiff(histDaily.temp, checkedHistDaily.temp, binding.tvTempValueC)
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
        val diffForm = df.format(diff)

        if (diff > 0){
            val diffStr = "+${diffForm}"
            textView.text = diffStr
            textView.setTextColor(Color.LTGRAY)
        } else if (diff < 0){
            textView.text = diffForm
            textView.setTextColor(Color.LTGRAY)
        } else {
            textView.text = diffForm
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

    /**
     * Shows alert with information about deleting saved forecast (it is forever). Next, delete
     * the model and move up.
     * */
    private fun showAlertAndProceed(){
        AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.caution))
                .setMessage(getString(R.string.delete_forever))
                .setPositiveButton(getString(R.string.yes)) { _ : DialogInterface, _ : Int ->
                    histDailyViewModel.deleteCurrentDaily()
                    findNavController().navigateUp()
                    Toast.makeText(requireContext(), getText(R.string.success), Toast.LENGTH_SHORT)
                            .show()
                }
                .setNegativeButton(getString(R.string.no)) { _ : DialogInterface, _ : Int ->

                }
                .create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_top, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menuDelete){
            showAlertAndProceed()
        }
        return super.onOptionsItemSelected(item)
    }
}