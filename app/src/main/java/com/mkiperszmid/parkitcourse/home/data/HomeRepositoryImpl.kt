package com.mkiperszmid.parkitcourse.home.data

import android.util.Log
import com.mkiperszmid.parkitcourse.BuildConfig
import com.mkiperszmid.parkitcourse.home.data.extension.resultOf
import com.mkiperszmid.parkitcourse.home.data.utils.PolylineUtils
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
            val originalRoute = api.getDirections(origin, dest, key = BuildConfig.MAPS_API_KEY).toRoute()
            Log.d("HomeRepositoryImpl", "Polyline points before simplification: ${originalRoute.polylines.size}")

            val simplifiedPolylines = PolylineUtils.simplify(originalRoute.polylines, 5.0)
            Log.d("HomeRepositoryImpl", "Polyline points after simplification: ${simplifiedPolylines.size}")

            val simplifiedRoute = originalRoute.copy(polylines = simplifiedPolylines)
            simplifiedRoute
        }
    }
}