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