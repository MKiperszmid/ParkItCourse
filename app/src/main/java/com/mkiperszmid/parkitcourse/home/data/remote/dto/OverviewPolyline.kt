package com.mkiperszmid.parkitcourse.home.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OverviewPolyline(
    @SerialName("points")
    val points: String
)