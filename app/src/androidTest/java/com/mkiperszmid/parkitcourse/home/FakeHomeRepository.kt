package com.mkiperszmid.parkitcourse.home

import com.mkiperszmid.parkitcourse.home.domain.HomeRepository
import com.mkiperszmid.parkitcourse.home.domain.model.Car
import com.mkiperszmid.parkitcourse.home.domain.model.Location
import com.mkiperszmid.parkitcourse.home.domain.model.Route

class FakeHomeRepository : HomeRepository {
    private var parkedCar: Car? = null

    override suspend fun parkCar(car: Car) {
        parkedCar = car
    }

    override suspend fun deleteCar(car: Car) {
        parkedCar = null
    }

    override suspend fun getParkedCar(): Car? {
        return parkedCar
    }

    override suspend fun getDirections(
        currentLocation: Location,
        destination: Location
    ): Result<Route> {
        return Result.success(
            Route(3000, emptyList())
        )
    }
}