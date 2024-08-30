package com.mkiperszmid.parkitcourse.home.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Leg(
    @SerialName("distance")
    val distance: Distance,
    @SerialName("duration")
    val duration: Duration,
    @SerialName("end_address")
    val endAddress: String,
    @SerialName("end_location")
    val endLocation: EndLocation,
    @SerialName("start_address")
    val startAddress: String,
    @SerialName("start_location")
    val startLocation: StartLocation,
    @SerialName("steps")
    val steps: List<Step>,
)