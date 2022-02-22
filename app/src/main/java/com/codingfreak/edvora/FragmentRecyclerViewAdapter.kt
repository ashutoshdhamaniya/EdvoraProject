package com.codingfreak.edvora

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

/*
* This Adapter class used to set data into recycler view
* */
class FragmentRecyclerViewAdapter(private val rideInfo: ArrayList<RideInfo>) :
    RecyclerView.Adapter<FragmentRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FragmentRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ride_info_card_layout, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FragmentRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.rideId.text = rideInfo[position].rideId
        holder.originStation.text = rideInfo[position].originStationCode.toString()
        holder.cityName.text = rideInfo[position].city
        holder.stateName.text = rideInfo[position].state

        var formatter = SimpleDateFormat("MMM yyyy HH:mm")
        val dateString: String = formatter.format(Date(rideInfo[position].date * 1000L))
        formatter = SimpleDateFormat("d")
        val dateNum: Int = formatter.format(Date(rideInfo[position].date * 1000L)).toInt()

        holder.date.text = "$dateNum${holder.getDayOfMonthSuffix(dateNum)} $dateString"

        holder.stationPath.text = rideInfo[position].stationPath.toString()
        holder.distance.text = rideInfo[position].distance.toString()
    }

    override fun getItemCount(): Int {
        return rideInfo.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.findViewById(R.id.cityName)
        val stateName: TextView = itemView.findViewById(R.id.stateName)
        val rideId: TextView = itemView.findViewById(R.id.rideId)
        val originStation: TextView = itemView.findViewById(R.id.originStation)
        val stationPath: TextView = itemView.findViewById(R.id.stationPath)
        val date: TextView = itemView.findViewById(R.id.date)
        val distance: TextView = itemView.findViewById(R.id.distance)
        val map: ImageView = itemView.findViewById(R.id.map)

        /*
        * this function is used to find suffix of the date
        * */
        fun getDayOfMonthSuffix(n: Int): String? {
            return if (n in 11..13) {
                "th"
            } else when (n % 10) {
                1 -> "st"
                2 -> "nd"
                3 -> "rd"
                else -> "th"
            }
        }
    }
}