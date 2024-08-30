package com.mkiperszmid.parkitcourse.home.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Distance(
    @SerialName("text")
    val text: String,
    @SerialName("value")
    val value: Int
)