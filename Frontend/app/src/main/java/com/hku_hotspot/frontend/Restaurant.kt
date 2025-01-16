package com.hku_hotspot.frontend

import com.google.android.gms.maps.model.LatLng

data class Restaurant(
    val id : Int,
    val name : String,
    val description : String,
    val link : String,
    val centerPosition: LatLng,
    val polygon: List<LatLng>,
    val capacity: Int,
    val imageResId: Int,
    var botCount: Int = 0, // Number of bots in the building
    var botPositions: MutableList<LatLng> = mutableListOf() // Positions of each bot in the building
)