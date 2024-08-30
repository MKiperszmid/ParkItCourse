package com.mkiperszmid.parkitcourse.home.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartLocation(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lng")
    val lng: Double
)