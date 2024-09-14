package com.mkiperszmid.parkitcourse.home.domain.model

data class Route(
    val distance: Int,
    val polylines: List<Location>
)
