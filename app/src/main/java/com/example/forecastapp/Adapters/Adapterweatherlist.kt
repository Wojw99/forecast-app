package com.example.forecastapp.Adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastapp.R
import com.example.forecastapp.model.Welcome

/*
class Adapterweatherlist(var weatherhistory: LiveData<Welcome>): RecyclerView.Adapter<Adapterweatherlist.Holder>() {

    class Holder(val view: View): RecyclerView.ViewHolder(view) {

        val datatv = view.findViewById<TextView>(R.id.tvdata)

        val coordtv1 = view.findViewById<TextView>(R.id.tvcoord1)
        val coordtv2 = view.findViewById<TextView>(R.id.tvcoord2)

        val correcttv = view.findViewById<TextView>(R.id.tvcorrect)

    }

    override  fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_one_row,parent, false) as View

        return Holder(view)
    }

    override fun getItemCount(): Int {

        return weatherhistory.value?.size?:0
    }

        override fun onBindViewHolder(holder: Holder, position: Int) {

            holder.coordtv1.text=weatherhistory.value?.get(position)?.lat?.toString()
            holder.coordtv1.text=weatherhistory.value?.get(position)?.lon?.toString()
            holder.datatv.text=weatherhistory.value?.get(position)?.timezone

        }
}
*/
