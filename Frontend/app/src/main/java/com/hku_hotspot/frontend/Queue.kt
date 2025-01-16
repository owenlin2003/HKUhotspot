package com.hku_hotspot.frontend

import com.google.android.gms.maps.model.LatLng

data class Queue(
    val id: Int,
    val name: String,
    val description: String,
    val capacity: Int,
    val centerPosition: LatLng,
    val points: List<LatLng>, // List of points representing the queue as a line
    var botCount: Int = 0,
    var botPositions: MutableList<LatLng> = mutableListOf(), // Positions of each bot along the queue
) {


    fun getOccupancyLevel(): Int {
        val fraction = botCount.toDouble() / capacity
        return when {
            fraction <= 0.2 -> 1 // 0-20%
            fraction <= 0.4 -> 2 // 21-40%
            fraction <= 0.6 -> 3 // 41-60%
            fraction <= 0.8 -> 4 // 61-80%
            else -> 5           // 81-100%
        }
    }
}

