package com.mkiperszmid.parkitcourse.home

import com.mkiperszmid.parkitcourse.home.domain.LocationService
import com.mkiperszmid.parkitcourse.home.domain.model.Location
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class FakeLocationService : LocationService {
    val locations = listOf(
        Location(10.0, 10.0),
        Location(15.0, 15.0),
        Location(20.0, 20.0)
    )
    var shouldTrack = false
    override fun getLocationUpdates() = flow {
        var count = 0
        shouldTrack = true
        while (shouldTrack) {
            emit(locations[count % locations.size])
            count++
            delay(1000)
        }
    }

    override fun stopLocationUpdates() {
        shouldTrack = false
    }

    override suspend fun getCurrentLocation(): Location? {
        return locations.first()
    }
}