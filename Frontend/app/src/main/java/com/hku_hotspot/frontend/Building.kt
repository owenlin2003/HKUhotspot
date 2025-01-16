package com.hku_hotspot.frontend

import com.google.android.gms.maps.model.LatLng

data class Building(
    val id: Int,
    val name: String,
    val description: String,
    val centerPosition: LatLng,
    val polygon: List<LatLng>,
    val capacity: Int,
    var botCount: Int = 0, // Number of bots in the building
    var botPositions: MutableList<LatLng> = mutableListOf() // Positions of each bot in the building
) {
    val status: String
        get() = when {
            botCount < capacity / 3 -> "Empty"
            botCount > 2 * capacity / 3 -> "Full"
            else -> "Occupied"
        }
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
