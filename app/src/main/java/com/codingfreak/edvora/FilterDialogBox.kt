package com.codingfreak.edvora

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDialogFragment

class FilterDialogBox() : AppCompatDialogFragment() {

    /*
    * In this we are entering the data manually
    * but when the data comes dynamically from database or Api then we pass the data dynamically
    * to the adapters of state and city items.
    * */

    val stateItems = arrayOf("State" , "StateName1" , "StateName2" , "StateName3" , "StateName4")
    val cityItems = arrayOf("CityName1" , "CityName2" , "CityName3" , "CityName4" , "CityName5")
    private lateinit var stateSpinner : Spinner
    private lateinit var citySpinner : Spinner

    @SuppressLint("ShowToast", "ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val dialogView = inflater.inflate(R.layout.filter_dialog_box , container , false)

        stateSpinner = dialogView.findViewById(R.id.stateSpinner)
        citySpinner = dialogView.findViewById(R.id.citySpinner)

        val stateadapter =
            context?.let { ArrayAdapter.createFromResource(it, R.array.state_items , R.layout.list_item) }
        stateadapter?.setDropDownViewResource(R.layout.list_item_1)
        stateSpinner.adapter = stateadapter

        val cityAdapter =
            context?.let { ArrayAdapter.createFromResource(it, R.array.city_items , R.layout.list_item) }
        cityAdapter?.setDropDownViewResource(R.layout.list_item_1)
        citySpinner.adapter = cityAdapter

        stateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        return dialogView
    }
}