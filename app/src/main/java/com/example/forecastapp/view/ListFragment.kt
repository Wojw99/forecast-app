package com.example.forecastapp.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.forecastapp.Adapters.Adapterweatherlist
import com.example.forecastapp.R
import com.example.forecastapp.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.Observer

class ListFragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        buttonentercorrect.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_listOpenFragment)
        }
    }

/*
lateinit var listViewModel: ListViewModel
    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var adapterweatherlist: Adapterweatherlist
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)


        viewManager = LinearLayoutManager(requireContext())

        adapterweatherlist = Adapterweatherlist(listViewModel.forecastBody)

        recyclerViewWeather.apply {
            adapter = adapterweatherlist
            layoutManager = viewManager
        }


       // listViewModel.forecastBody.observe(viewLifecycleOwner, Observer {
       //     adapterweatherlist.notifyDataSetChanged()
       // })
    }

     */
}