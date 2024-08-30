package com.mkiperszmid.parkitcourse.home.data.mapper

import com.mkiperszmid.parkitcourse.home.data.local.entity.CarEntity
import com.mkiperszmid.parkitcourse.home.domain.model.Car

fun CarEntity.toDomain(): Car {
    return Car(
        id = this.id,
        latitude = this.latitude,
        longitude = this.longitude
    )
}

fun Car.toEntity(): CarEntity {
    return CarEntity(
        id = this.id ?: 0,
        latitude = this.latitude,
        longitude = this.longitude
    )
}