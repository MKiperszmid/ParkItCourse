package com.mkiperszmid.parkitcourse.home.data

import com.mkiperszmid.parkitcourse.BuildConfig
import com.mkiperszmid.parkitcourse.home.data.extension.resultOf
import com.mkiperszmid.parkitcourse.home.data.local.HomeDao
import com.mkiperszmid.parkitcourse.home.data.mapper.toDomain
import com.mkiperszmid.parkitcourse.home.data.mapper.toEntity
import com.mkiperszmid.parkitcourse.home.data.mapper.toRoute
import com.mkiperszmid.parkitcourse.home.data.remote.DirectionsApi
import com.mkiperszmid.parkitcourse.home.domain.HomeRepository
import com.mkiperszmid.parkitcourse.home.domain.model.Car
import com.mkiperszmid.parkitcourse.home.domain.model.Location
import com.mkiperszmid.parkitcourse.home.domain.model.Route

class HomeRepositoryImpl(
    private val dao: HomeDao,
    private val api: DirectionsApi
) : HomeRepository {
    override suspend fun parkCar(car: Car) {
        dao.insertCar(car.toEntity())
    }

    override suspend fun deleteCar(car: Car) {
        dao.deleteCar(car.toEntity())
    }

    override suspend fun getParkedCar(): Car? {
        return dao.getParkedCar()?.toDomain()
    }

    override suspend fun getDirections(
        currentLocation: Location,
        destination: Location
    ): Result<Route> {
        val origin = "${currentLocation.latitude},${currentLocation.longitude}"
        val dest = "${destination.latitude},${destination.longitude}"

        return resultOf {
            api.getDirections(origin, dest, key = BuildConfig.MAPS_API_KEY).toRoute()
        }
    }
}