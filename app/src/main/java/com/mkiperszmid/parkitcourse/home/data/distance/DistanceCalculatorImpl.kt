package com.mkiperszmid.parkitcourse.home.data.distance

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.utils.isLocationOnPath
import com.mkiperszmid.parkitcourse.home.domain.distance.DistanceCalculator
import com.mkiperszmid.parkitcourse.home.domain.model.Location

class DistanceCalculatorImpl : DistanceCalculator {
    override fun calculateDistance(startLocation: Location, endLocation: Location): Float {
        val result = FloatArray(1)
        android.location.Location.distanceBetween(
            startLocation.latitude,
            startLocation.longitude,
            endLocation.latitude,
            endLocation.longitude,
            result
        )
        return result.first()
    }

    override fun isLocationOnPath(
        path: List<Location>,
        location: Location,
        maxMeters: Double
    ): Boolean {
        return path.map { LatLng(it.latitude, it.longitude) }.isLocationOnPath(
            LatLng(location.latitude, location.longitude),
            false,
            maxMeters
        )
    }
}