package com.mkiperszmid.parkitcourse.home.domain.usecase

import android.util.Log
import com.mkiperszmid.parkitcourse.home.domain.HomeRepository
import com.mkiperszmid.parkitcourse.home.domain.distance.DistanceCalculator
import com.mkiperszmid.parkitcourse.home.domain.model.Location
import com.mkiperszmid.parkitcourse.home.domain.model.Route
import kotlin.math.roundToInt

class GetPathToCarUseCase(
    private val repository: HomeRepository,
    private val distanceCalculator: DistanceCalculator
) {
    companion object {
        const val MAX_METERS = 50.0
    }

    suspend operator fun invoke(
        currentLocation: Location,
        destination: Location,
        route: Route
    ): Result<Route> {
        val isOnRoute =
            distanceCalculator.isLocationOnPath(route.polylines, currentLocation, MAX_METERS)

        return if (isOnRoute) {
            Log.d("GetPathToCarUseCase", "User is on route. Recalculating locally.")
            val closestIndex = getClosestLocationIndex(currentLocation, route.polylines)
            val newPolyline = route.polylines.drop(closestIndex)
            val distance = distanceCalculator.calculateDistance(currentLocation, newPolyline.last())

            Result.success(
                route.copy(
                    polylines = newPolyline,
                    distance = distance.roundToInt()
                )
            )
        } else {
            Log.d("GetPathToCarUseCase", "User is off route. Fetching new directions from repository.")
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
            val distance = distanceCalculator.calculateDistance(currentLocation, polyline[i])
            if (distance < minDistance) {
                minDistance = distance
                closestIndex = i
            }
        }
        return closestIndex
    }

}