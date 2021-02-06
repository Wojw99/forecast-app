package com.example.forecastapp.view

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastapp.R
import com.example.forecastapp.model.HistDaily
import com.example.forecastapp.viewmodel.HistDailyViewModel
import java.time.format.DateTimeFormatter
import java.util.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.Holder>() {
    private var forecastList = emptyList<HistDaily>()

    class Holder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_one_row, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentItem = forecastList[position]
        val tvDate = holder.itemView.findViewById<TextView>(R.id.tvDate)
        val tvGeo = holder.itemView.findViewById<TextView>(R.id.tvGeo)
        val tvAccuracy = holder.itemView.findViewById<TextView>(R.id.tvAccuracy)
        val btnOpen = holder.itemView.findViewById<ImageButton>(R.id.buttonOpen)

        // Date
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formattedDate = currentItem.forecastDate.format(formatter)
        tvDate.text = formattedDate

        // Coordinates
        if(currentItem.city.isEmpty()){
            val directionX = if (currentItem.lon > 0) "E" else "W"
            val directionY = if (currentItem.lat > 0) "N" else "S"
            val geoStr = "${currentItem.lat} $directionY, ${currentItem.lon} $directionX"
            tvGeo.text = geoStr
        }else{
            tvGeo.text = currentItem.city
        }

        // Accuracy
        val accuracyStr = "Poprawność: ${currentItem.accuracy}"
        tvAccuracy.text = accuracyStr

        // Button open
        btnOpen.setOnClickListener {
            HistDailyViewModel.currentId = currentItem.id
            holder.itemView.findNavController().navigate(R.id.action_listFragment_to_listOpenFragment)
        }
    }

    fun setData(list: List<HistDaily>){
        this.forecastList = list
        notifyDataSetChanged()
    }
}