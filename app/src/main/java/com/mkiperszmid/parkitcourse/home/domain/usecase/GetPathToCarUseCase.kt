package com.mkiperszmid.parkitcourse.home.domain.usecase

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.utils.isLocationOnPath
import com.mkiperszmid.parkitcourse.home.domain.HomeRepository
import com.mkiperszmid.parkitcourse.home.domain.model.Location
import com.mkiperszmid.parkitcourse.home.domain.model.Route
import kotlin.math.roundToInt

class GetPathToCarUseCase(private val repository: HomeRepository) {
    companion object {
        const val MAX_METERS = 30.0
    }

    suspend operator fun invoke(
        currentLocation: Location,
        destination: Location,
        route: Route
    ): Result<Route> {
        val closestIndex = getClosestLocationIndex(currentLocation, route.polylines)
        val isOnRoute = route.polylines.map { LatLng(it.latitude, it.longitude) }.isLocationOnPath(
            LatLng(currentLocation.latitude, currentLocation.longitude),
            false,
            MAX_METERS
        )

        return if (isOnRoute) {
            val newPolyline = route.polylines.drop(closestIndex)
            println("Estas en camino!")
            val distance = calculateDistanceBetweenLocations(currentLocation, newPolyline.last())

            Result.success(
                route.copy(
                    polylines = newPolyline,
                    distance = distance.roundToInt()
                )
            )
        } else {
            println("Calculando nueva ruta!")
            repository.getDirections(currentLocation, destination)
        }
    }

    private fun getClosestLocationIndex(
        currentLocation: Location,
        polyline: List<Location>
    ): Int {
        var minDistance = Float.MAX_VALUE
        var closestIndex = 0

        for (i in polyline.indices) {
            val distance = calculateDistanceBetweenLocations(currentLocation, polyline[i])
            if (distance < minDistance) {
                minDistance = distance
                closestIndex = i
            }
        }
        return closestIndex
    }

    private fun calculateDistanceBetweenLocations(
        firstLocation: Location,
        secondLocation: Location,
    ): Float {
        val result = FloatArray(1)
        android.location.Location.distanceBetween(
            firstLocation.latitude,
            firstLocation.longitude,
            secondLocation.latitude,
            secondLocation.longitude,
            result
        )
        return result.first()
    }
}