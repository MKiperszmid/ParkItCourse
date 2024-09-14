package com.mkiperszmid.parkitcourse.home.presentation

import com.mkiperszmid.parkitcourse.home.domain.model.Car
import com.mkiperszmid.parkitcourse.home.domain.model.Location
import com.mkiperszmid.parkitcourse.home.domain.model.Route

data class HomeState(
    val carStatus: CarStatus = CarStatus.NO_PARKED_CAR,
    val currentLocation: Location? = null,
    val car: Car? = null,
    val route: Route? = null,
    val hasRequiredPermissions: Boolean = false
)

enum class CarStatus {
    NO_PARKED_CAR, PARKED_CAR, SEARCHING
}