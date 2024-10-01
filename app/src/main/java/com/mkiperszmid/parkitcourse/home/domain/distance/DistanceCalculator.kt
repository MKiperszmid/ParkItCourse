package com.mkiperszmid.parkitcourse.home.domain.distance

import com.mkiperszmid.parkitcourse.home.domain.model.Location

interface DistanceCalculator {
    fun calculateDistance(
        startLocation: Location,
        endLocation: Location,
    ): Float

    fun isLocationOnPath(
        path: List<Location>,
        location: Location,
        maxMeters: Double
    ): Boolean
}