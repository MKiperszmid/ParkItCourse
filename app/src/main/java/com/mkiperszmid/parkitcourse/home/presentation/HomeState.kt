package com.mkiperszmid.parkitcourse.home.presentation

import com.mkiperszmid.parkitcourse.home.domain.model.Car
import com.mkiperszmid.parkitcourse.home.domain.model.Location

data class HomeState(
    val carStatus: CarStatus = CarStatus.NO_PARKED_CAR,
    val currentLocation: Location? = null,
    val car: Car? = null
)

enum class CarStatus {
    NO_PARKED_CAR, PARKED_CAR, SEARCHING
}