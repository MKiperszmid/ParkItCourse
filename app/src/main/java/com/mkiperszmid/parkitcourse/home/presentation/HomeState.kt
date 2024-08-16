package com.mkiperszmid.parkitcourse.home.presentation

import android.location.Location


data class HomeState(
    val carStatus: CarStatus = CarStatus.NO_PARKED_CAR,
    val currentLocation: Location? = null
)

enum class CarStatus {
    NO_PARKED_CAR, PARKED_CAR, SEARCHING
}