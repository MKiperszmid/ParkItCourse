package com.mkiperszmid.parkitcourse.home.domain

import com.mkiperszmid.parkitcourse.home.domain.model.Car
import com.mkiperszmid.parkitcourse.home.domain.model.Location
import com.mkiperszmid.parkitcourse.home.domain.model.Route

interface HomeRepository {
    suspend fun parkCar(car: Car)
    suspend fun deleteCar(car: Car)
    suspend fun getParkedCar(): Car?
    suspend fun getDirections(currentLocation: Location, destination: Location): Result<Route>
}