package com.codingfreak.edvora

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainFragment() : Fragment() {

    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        /*
        * Extracting data from bundle
        * */
        var rideInfoArrayList: ArrayList<RideInfo> =
            arguments?.getSerializable("rideInfoArrayList") as ArrayList<RideInfo>
        rideInfoArrayList.sortWith(compareBy({ it.distance }))
        Log.d("Ashu", rideInfoArrayList.toString())

        /*
        * Knowing which fragment we are currently on using tabID
        *
        * When all the data comes dynamically from an API then this is used to differentiate Fragments and
        * set correct data to correct fragments
        *
        * currently data about past rides is passed empty
        * this can we updated when we are getting data dynamically
        * */
        if (arguments?.getInt("tabId") == 2) {
            rideInfoArrayList = ArrayList()
        }

        // Setting recycler view and layout manager
        recyclerView.layoutManager = LinearLayoutManager(context)
        val fragmentRecyclerViewAdapter = FragmentRecyclerViewAdapter(rideInfoArrayList)
        recyclerView.adapter = fragmentRecyclerViewAdapter

        return view
    }
}