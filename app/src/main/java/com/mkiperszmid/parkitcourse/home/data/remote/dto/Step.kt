package com.mkiperszmid.parkitcourse.home.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Step(
    @SerialName("distance")
    val distance: Distance,
    @SerialName("duration")
    val duration: Duration,
    @SerialName("end_location")
    val endLocation: EndLocation,
    @SerialName("html_instructions")
    val htmlInstructions: String,
    @SerialName("maneuver")
    val maneuver: String?,
    @SerialName("polyline")
    val polyline: Polyline,
    @SerialName("start_location")
    val startLocation: StartLocation,
    @SerialName("travel_mode")
    val travelMode: String
)