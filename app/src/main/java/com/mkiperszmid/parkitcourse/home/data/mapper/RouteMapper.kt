package com.mkiperszmid.parkitcourse.home.data.mapper

import com.google.maps.android.ktx.utils.toLatLngList
import com.mkiperszmid.parkitcourse.home.data.remote.dto.DirectionsResponse
import com.mkiperszmid.parkitcourse.home.domain.model.Route

fun DirectionsResponse.toRoute(): Route {
    val firstRoute = this.routes.first()
    val firstLeg = firstRoute.legs.first()

    val routePoints = firstLeg.steps.flatMap {
        it.polyline.points.toLatLngList()
    }
    return Route(
        distance = firstLeg.distance.value,
        polylines = routePoints.map { it.toLocation() }
    )
}