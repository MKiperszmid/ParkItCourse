package com.mkiperszmid.parkitcourse.home.domain

import com.mkiperszmid.parkitcourse.home.domain.model.Car

interface HomeRepository {
    suspend fun parkCar(car: Car)
    suspend fun deleteCar(car: Car)
    suspend fun getParkedCar(): Car?
}