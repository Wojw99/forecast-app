package com.example.forecastapp.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forecastapp.databinding.FragmentListBinding
import com.example.forecastapp.model.HistDaily
import com.example.forecastapp.viewmodel.HistDailyViewModel
import java.time.LocalDate
import java.time.Month
import java.util.*
import java.time.LocalDateTime
import java.time.LocalTime


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    lateinit var histDailyViewModel: HistDailyViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)

        histDailyViewModel = ViewModelProvider(this).get(HistDailyViewModel::class.java)
        setupRecyclerView()


        //addingTest()

        //Testing room update function

       // val hweather = HistDaily(1,4, LocalDate.of(2015, Month.APRIL, 15),LocalDate.of(2016, Month.APRIL, 16),"city",3.45,5.45,"accuracy5",5,5,6.54,8.76,9,8,6.54,6.89,4.34,"weather",9.0,8.0,7.0)
       // val hweather2 = HistDaily(2,6, LocalDate.of(2019, Month.APRIL, 16),LocalDate.of(2020, Month.APRIL, 16),"city",3.45,5.45,"accuracy2",5,5,6.54,8.76,9,8,6.54,6.89,4.34,"weather",9.0,8.0,7.0)
       // histDailyViewModel.addhistoricalweather(hweather2)
        //histDailyViewModel.addhistoricalweather(hweather)
        //histDailyViewModel.addhistoricalweather(hweather2)
       // // histDailyViewModel.edithistoricalweather(hweather2)
       // histDailyViewModel.deletehistoricalweather(hweather)
       // histDailyViewModel.deletehistoricalweather(hweather)
       // histDailyViewModel.deletallweathers()
       // var test = histDailyViewModel.readAll
        //var test = histDailyViewModel.weatherbyid(1)
        //var test2 = histDailyViewModel.weatherbyid(2)
       // Log.d("test",test.toString())
        //Log.d("test2",test2.toString())


        return binding.root
    }

    private fun setupRecyclerView(){
        val adapter = ListAdapter()
        binding.recyclerViewList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewList.adapter = adapter
        histDailyViewModel.readAll.observe(viewLifecycleOwner, Observer { daily ->
            adapter.setData(daily)

        })
    }

}