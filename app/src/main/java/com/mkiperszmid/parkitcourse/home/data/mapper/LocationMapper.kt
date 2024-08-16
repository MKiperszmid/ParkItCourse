package com.mkiperszmid.parkitcourse.home.data.mapper

import android.location.Location

fun Location.toDomain(): com.mkiperszmid.parkitcourse.home.domain.model.Location {
    return com.mkiperszmid.parkitcourse.home.domain.model.Location(
        latitude = this.latitude,
        longitude = this.longitude
    )
}