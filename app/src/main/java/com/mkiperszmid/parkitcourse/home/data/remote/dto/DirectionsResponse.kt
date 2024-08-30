package com.mkiperszmid.parkitcourse.home.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DirectionsResponse(
    @SerialName("geocoded_waypoints")
    val geocodedWaypoints: List<GeocodedWaypoint>,
    @SerialName("routes")
    val routes: List<Route>,
    @SerialName("status")
    val status: String
)