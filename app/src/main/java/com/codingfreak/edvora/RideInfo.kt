package com.codingfreak.edvora

import org.json.JSONArray

/*
* RideInfo is the data class which contains all the details of ride
* */

class RideInfo(
    val rideId: String,
    val originStationCode: Int,
    val stationPath: JSONArray,
    val destinationStationCode: Int,
    val date: Long,
    val mapUrl: String,
    val state: String,
    val city: String,
    var distance : Int = 0,
)