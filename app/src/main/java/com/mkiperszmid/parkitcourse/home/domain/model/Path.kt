package com.mkiperszmid.parkitcourse.home.domain.model

data class Path(
    val location: Location,
    val distance: Int,
    val street: String,
    val duration: Int,
    val steps: Int
)
