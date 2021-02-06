package com.example.forecastapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.forecastapp.R

class ListOpenFragment : Fragment() {

    private var historicalDailyWeatherModel1 = "pogoda1"
    private var historicalDailyWeatherModel2 = "pogoda2"

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_open, container, false)
    }
}