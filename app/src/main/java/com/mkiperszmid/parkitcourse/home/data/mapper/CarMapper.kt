package com.mkiperszmid.parkitcourse.home.data.mapper

import com.mkiperszmid.parkitcourse.home.data.local.entity.CarEntity
import com.mkiperszmid.parkitcourse.home.domain.model.Car
import com.mkiperszmid.parkitcourse.home.domain.model.Location

fun CarEntity.toDomain(): Car {
    return Car(
        id = this.id,
        location = Location(latitude = this.latitude, longitude = this.longitude),
    )
}

fun Car.toEntity(): CarEntity {
    return CarEntity(
        id = this.id ?: 0,
        latitude = this.location.latitude,
        longitude = this.location.longitude
    )
}