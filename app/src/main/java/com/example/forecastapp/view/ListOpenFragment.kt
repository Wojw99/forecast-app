package com.example.forecastapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.forecastapp.databinding.FragmentListOpenBinding
import com.example.forecastapp.model.CheckedHistDaily
import com.example.forecastapp.model.HistDaily
import com.example.forecastapp.viewmodel.HistDailyViewModel


class ListOpenFragment : Fragment() {
    private lateinit var histDailyViewModel : HistDailyViewModel

    private var _binding: FragmentListOpenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListOpenBinding.inflate(inflater, container, false)
        val view = binding.root

        histDailyViewModel = ViewModelProvider(this).get(HistDailyViewModel::class.java)
        setup()

        return view
    }

    private fun updateHistDailyView(histDaily: HistDaily){
        // Tu musi zostać rozpisane aktulizowanie się widoku pogody prognozowanej
        Log.d("Update", "id: ${histDaily.id}, city: ${histDaily.city}, temp: ${histDaily.temp}, accuracy: ${histDaily.accuracy}, hum:${histDaily.humidity}")
    }

    private fun updateCheckedHistDailyView(checkedHistDaily: CheckedHistDaily){
        // Tu musi zostać rozpisane aktulizowanie się widoku pogody rzeczywistej
        Log.d("Update", "id: ${checkedHistDaily.id}, temp: ${checkedHistDaily.temp}, hum:${checkedHistDaily.humidity}")
    }

    private fun setup(){
        histDailyViewModel.histDaily.observe(viewLifecycleOwner, Observer { daily ->
            updateHistDailyView(daily)
        })

        histDailyViewModel.checkedHistDaily.observe(viewLifecycleOwner, Observer { daily ->
            updateCheckedHistDailyView(daily)
        })

        histDailyViewModel.readCheckedHistDailyById(HistDailyViewModel.currentId)
        histDailyViewModel.readHistDailyById(HistDailyViewModel.currentId)
    }
}