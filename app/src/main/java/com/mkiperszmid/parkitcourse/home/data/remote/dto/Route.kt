package com.mkiperszmid.parkitcourse.home.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Route(
    @SerialName("bounds")
    val bounds: Bounds,
    @SerialName("copyrights")
    val copyrights: String,
    @SerialName("legs")
    val legs: List<Leg>,
    @SerialName("overview_polyline")
    val overviewPolyline: OverviewPolyline,
    @SerialName("summary")
    val summary: String,
    @SerialName("warnings")
    val warnings: List<String>
)