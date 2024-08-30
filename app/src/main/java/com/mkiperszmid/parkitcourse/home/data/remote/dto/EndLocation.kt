package com.mkiperszmid.parkitcourse.home.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EndLocation(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lng")
    val lng: Double
)