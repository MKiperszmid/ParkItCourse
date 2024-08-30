package com.mkiperszmid.parkitcourse.home.data

import com.mkiperszmid.parkitcourse.home.data.local.HomeDao
import com.mkiperszmid.parkitcourse.home.data.mapper.toDomain
import com.mkiperszmid.parkitcourse.home.data.mapper.toEntity
import com.mkiperszmid.parkitcourse.home.domain.HomeRepository
import com.mkiperszmid.parkitcourse.home.domain.model.Car

class HomeRepositoryImpl(
    private val dao: HomeDao
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
}