package com.example.forecastapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastapp.R
import com.example.forecastapp.model.HistDailyTest
import com.example.forecastapp.model.HistoricalDailyModel
import java.util.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.Holder>() {
    private var forecastList = emptyList<HistDailyTest>()

    class Holder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_one_row, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentItem = forecastList[position]
        val tvDate = holder.itemView.findViewById<TextView>(R.id.tvDate)
        val tvGeo = holder.itemView.findViewById<TextView>(R.id.tvGeo)
        val tvAccuracy = holder.itemView.findViewById<TextView>(R.id.tvAccuracy)
        val btnOpen = holder.itemView.findViewById<ImageButton>(R.id.buttonOpen)

        // Date
        val calendar = GregorianCalendar()
        calendar.time = currentItem.forecastDate
        val dateStr = "${calendar.get(Calendar.DAY_OF_MONTH)}.${calendar.get(Calendar.MONTH + 1)}.${calendar.get(Calendar.YEAR)}"
        tvDate.text = dateStr

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

        }
    }

    fun setData(list: List<HistDailyTest>){
        this.forecastList = list
        notifyDataSetChanged()
    }
}