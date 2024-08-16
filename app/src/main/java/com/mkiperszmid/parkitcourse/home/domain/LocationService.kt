package com.mkiperszmid.parkitcourse.home.domain

import com.mkiperszmid.parkitcourse.home.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationService {
    fun getLocationUpdates(): Flow<Location?>
    fun stopLocationUpdates()
    suspend fun getCurrentLocation(): Location?
}