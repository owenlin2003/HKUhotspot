package com.hku_hotspot.frontend

import com.google.android.gms.maps.model.LatLng
import kotlin.random.Random

// Function to generate random positions for bots within a building's polygon
fun generateRandomPositionInPolygon(polygon: List<LatLng>): LatLng {
    val latitudes = polygon.map { it.latitude }
    val longitudes = polygon.map { it.longitude }

    val minLat = latitudes.minOrNull() ?: 0.0
    val maxLat = latitudes.maxOrNull() ?: 0.0
    val minLng = longitudes.minOrNull() ?: 0.0
    val maxLng = longitudes.maxOrNull() ?: 0.0

    var point: LatLng
    do {
        val randomLat = Random.nextDouble(minLat, maxLat)
        val randomLng = Random.nextDouble(minLng, maxLng)
        point = LatLng(randomLat, randomLng)
    } while (!isPointInPolygon(point, polygon))

    return point
}

// Check if a point is inside a polygon using the ray-casting algorithm
fun isPointInPolygon(point: LatLng, polygon: List<LatLng>): Boolean {
    var intersects = false
    for (i in polygon.indices) {
        val vertex1 = polygon[i]
        val vertex2 = polygon[(i + 1) % polygon.size]
        if (((vertex1.latitude > point.latitude) != (vertex2.latitude > point.latitude)) &&
            (point.longitude < (vertex2.longitude - vertex1.longitude) * (point.latitude - vertex1.latitude) / (vertex2.latitude - vertex1.latitude) + vertex1.longitude)
        ) {
            intersects = !intersects
        }
    }
    return intersects
}

// Assign random bot counts to each building
fun updateBuildingsWithBots(buildings: List<Building>) {
    buildings.forEach { building ->
        building.botCount = (0..building.capacity).random() // Random bot count

        // Clear previous bot positions
        building.botPositions.clear()

        // Generate random positions for each bot within the building’s polygon
        repeat(building.botCount) {
            val botPosition = generateRandomPositionInPolygon(building.polygon)
            building.botPositions.add(botPosition)
        }
    }
}


// Assign random bot counts and positions to each queue
fun updateQueuesWithBots(queues: List<Queue>) {
    queues.forEach { queue ->
        queue.botCount = (0..queue.capacity).random()
        queue.botPositions.clear()

        // Generate random positions along the queue line
        repeat(queue.botCount) {
            val segment = Random.nextInt(queue.points.size - 1)
            val start = queue.points[segment]
            val end = queue.points[segment + 1]
            val lat = start.latitude + (end.latitude - start.latitude) * Math.random()
            val lng = start.longitude + (end.longitude - start.longitude) * Math.random()
            queue.botPositions.add(LatLng(lat, lng))
        }
    }
}



// Get all bot positions from buildings and queues
//Beginning for Find my friend feature

/*fun getBotPositions(buildingsList: List<Building>, queuesList: List<Queue>): List<LatLng> {
    val botPositions = mutableListOf<LatLng>()

    // Add bot positions from buildings
    buildingsList.forEach { building ->
        botPositions.addAll(building.botPositions)
    }

    // Add bot positions from queues
    queuesList.forEach { queue ->
        botPositions.addAll(queue.botPositions)
    }
}

    return botPositions */

