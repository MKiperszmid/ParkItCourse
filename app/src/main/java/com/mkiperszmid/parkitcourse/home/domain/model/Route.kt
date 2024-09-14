package com.mkiperszmid.parkitcourse.home.domain.model

data class Route(
    val distance: Int,
    val duration: String,
    val paths: List<Path>,
    val polylines: List<Location>
)
