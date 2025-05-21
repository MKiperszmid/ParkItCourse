package com.mkiperszmid.parkitcourse.home.data.utils

import com.mkiperszmid.parkitcourse.home.domain.model.Location // Ensure this import is added
import org.junit.Assert.*
import org.junit.Test

class PolylineUtilsTest {

    @Test
    fun `simplify with empty list returns empty list`() {
        val points = emptyList<Location>()
        val simplified = PolylineUtils.simplify(points, 1.0)
        assertTrue(simplified.isEmpty())
    }

    @Test
    fun `simplify with less than 3 points returns original list`() {
        val points = listOf(Location(0.0, 0.0), Location(1.0, 1.0))
        val simplified = PolylineUtils.simplify(points, 1.0)
        assertEquals(points, simplified)
    }

    @Test
    fun `simplify straight line removes intermediate points`() {
        val points = listOf(
            Location(0.0, 0.0),
            Location(1.0, 1.0), // This point should be removed
            Location(2.0, 2.0)
        )
        val expected = listOf(
            Location(0.0, 0.0),
            Location(2.0, 2.0)
        )
        val simplified = PolylineUtils.simplify(points, 1.0) // Epsilon allows removal
        assertEquals(expected, simplified)
    }

    @Test
    fun `simplify straight line keeps intermediate points if epsilon is too small`() {
        val points = listOf(
            Location(0.0, 0.0),
            Location(1.0, 1.0), // This point should be kept
            Location(2.0, 2.0)
        )
        // Epsilon is smaller than the perpendicular distance of (1,1) to the line (0,0)-(2,2),
        // but RDP for a line of 3 points where the middle one is *on* the segment
        // should still simplify it to 2 points if the middle point is not an endpoint.
        // The perpendicular distance of (1,1) to line (0,0)-(2,2) is 0.
        // Let's test with a point slightly off the line.
        val pointsSlightlyOff = listOf(
            Location(0.0, 0.0),
            Location(1.0, 1.01), // Slightly off
            Location(2.0, 2.0)
        )
        // If epsilon is very small (e.g., 0.001), (1.0, 1.01) should be kept.
        val simplifiedKept = PolylineUtils.simplify(pointsSlightlyOff, 0.001)
        assertEquals(pointsSlightlyOff, simplifiedKept)

        // If epsilon is larger (e.g., 0.1), (1.0, 1.01) should be removed.
        val expectedRemoved = listOf(Location(0.0, 0.0), Location(2.0, 2.0))
        val simplifiedRemoved = PolylineUtils.simplify(pointsSlightlyOff, 0.1)
        assertEquals(expectedRemoved, simplifiedRemoved)
    }

    @Test
    fun `simplify simple shape (triangle)`() {
        val points = listOf(
            Location(0.0, 0.0), // A
            Location(1.0, 1.0), // B (should be kept if epsilon is small enough relative to height)
            Location(2.0, 0.0), // C
            Location(0.0, 0.0)  // D (closes the triangle, same as A)
        )
        // For the segment A-C, point B is 1.0 unit away.
        // Expected: A, B, C (which is A again for the last segment)
        // RDP processes segments. (A,B,C) then (C,D) which is (C,A)
        // If we simplify A,B,C with large epsilon, we get A,C.
        // If we simplify A,B,C,D:
        // 1. Initial call with (A,B,C,D), epsilon. First point A, last D (which is A).
        //    Max distance point from line A-D (which is line A-A, a point) is B or C.
        //    This scenario highlights that RDP is for polylines, not polygons directly unless handled.
        //    Let's test an open polyline that forms part of a triangle.
        val openTrianglePoints = listOf(
            Location(0.0, 0.0), // A
            Location(1.0, 1.0), // B
            Location(2.0, 0.0)  // C
        )
        // Perpendicular distance of B to line AC is 1.0.
        // If epsilon < 1.0, B is kept.
        val simplifiedKeptB = PolylineUtils.simplify(openTrianglePoints, 0.5)
        assertEquals(openTrianglePoints, simplifiedKeptB)

        // If epsilon > 1.0, B is removed.
        val expectedRemovedB = listOf(Location(0.0, 0.0), Location(2.0, 0.0))
        val simplifiedRemovedB = PolylineUtils.simplify(openTrianglePoints, 1.5)
        assertEquals(expectedRemovedB, simplifiedRemovedB)
    }

    @Test
    fun `simplify more complex shape`() {
        val points = listOf(
            Location(0.0, 0.0), // A
            Location(1.0, 0.0), // B
            Location(2.0, 1.0), // C - peak
            Location(3.0, 0.0), // D
            Location(4.0, 0.0)  // E
        )
        // For line A-E, point C is the furthest. Perpendicular distance is 1.0.
        // If epsilon is < 1.0, C should be kept.
        // Then it splits into A-C and C-E.
        // For A-C: B is on the line segment. It should be removed.
        // For C-E: D is on the line segment. It should be removed.
        // Expected: A, C, E
        val expected = listOf(
            Location(0.0, 0.0),
            Location(2.0, 1.0),
            Location(4.0, 0.0)
        )
        val simplified = PolylineUtils.simplify(points, 0.5) // Epsilon < 1.0
        assertEquals(expected, simplified)

        // If epsilon is > 1.0, C is removed, resulting in just A, E
        val expectedMoreSimplified = listOf(
            Location(0.0, 0.0),
            Location(4.0, 0.0)
        )
        val moreSimplified = PolylineUtils.simplify(points, 1.5) // Epsilon > 1.0
        assertEquals(expectedMoreSimplified, moreSimplified)
    }
}
