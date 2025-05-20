package com.mkiperszmid.parkitcourse.home.data.utils

import com.mkiperszmid.parkitcourse.home.domain.model.Location
import kotlin.math.pow
import kotlin.math.sqrt

object PolylineUtils {

    fun simplify(pointList: List<Location>, epsilon: Double): List<Location> {
        if (pointList.size < 3) {
            return pointList // Cannot simplify lines with fewer than 3 points
        }
        val epsilonSquared = epsilon.pow(2) // Use squared epsilon for squared distance comparisons
        return rdp(pointList, epsilonSquared)
    }

    private fun rdp(pointList: List<Location>, epsilonSquared: Double): List<Location> {
        if (pointList.size < 3) {
            return pointList
        }

        var maxDistance = 0.0
        var index = 0

        val firstPoint = pointList.first()
        val lastPoint = pointList.last()

        for (i in 1 until pointList.size - 1) {
            val distance = perpendicularDistanceSquared(pointList[i], firstPoint, lastPoint)
            if (distance > maxDistance) {
                maxDistance = distance
                index = i
            }
        }

        return if (maxDistance > epsilonSquared) {
            // Recursive call
            val recResults1 = rdp(pointList.subList(0, index + 1), epsilonSquared)
            val recResults2 = rdp(pointList.subList(index, pointList.size), epsilonSquared)

            // Combine results, excluding the duplicated point (index) from recResults2
            listOf(recResults1.dropLast(1), recResults2).flatten()
        } else {
            // All points within epsilon, so keep only first and last
            listOf(firstPoint, lastPoint)
        }
    }

    private fun perpendicularDistanceSquared(
        point: Location,
        lineStart: Location,
        lineEnd: Location
    ): Double {
        val dx = lineEnd.latitude - lineStart.latitude
        val dy = lineEnd.longitude - lineStart.longitude

        if (dx == 0.0 && dy == 0.0) { // lineStart and lineEnd are the same point
            return point.distanceSquaredTo(lineStart)
        }

        val t = ((point.latitude - lineStart.latitude) * dx + (point.longitude - lineStart.longitude) * dy) /
                (dx.pow(2) + dy.pow(2))

        val closestPoint: Location = when {
            t < 0 -> lineStart // Closest point is the start of the segment
            t > 1 -> lineEnd   // Closest point is the end of the segment
            else -> Location(lineStart.latitude + t * dx, lineStart.longitude + t * dy) // Projection falls on the segment
        }
        return point.distanceSquaredTo(closestPoint)
    }

    // Helper extension function for Location class (assuming Location has latitude and longitude)
    private fun Location.distanceSquaredTo(other: Location): Double {
        val dLat = this.latitude - other.latitude
        val dLon = this.longitude - other.longitude
        return dLat.pow(2) + dLon.pow(2)
    }
}
