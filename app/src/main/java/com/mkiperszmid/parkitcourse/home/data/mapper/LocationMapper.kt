package com.mkiperszmid.parkitcourse.home.data.mapper

import android.location.Location
import com.google.android.gms.maps.model.LatLng

fun Location.toDomain(): com.mkiperszmid.parkitcourse.home.domain.model.Location {
    return com.mkiperszmid.parkitcourse.home.domain.model.Location(
        latitude = this.latitude,
        longitude = this.longitude
    )
}

fun LatLng.toLocation(): com.mkiperszmid.parkitcourse.home.domain.model.Location {
    return com.mkiperszmid.parkitcourse.home.domain.model.Location(
        latitude = this.latitude,
        longitude = this.longitude
    )
}