import com.mkiperszmid.parkitcourse.home.domain.HomeRepository
import com.mkiperszmid.parkitcourse.home.domain.distance.DistanceCalculator
import com.mkiperszmid.parkitcourse.home.domain.model.Location
import com.mkiperszmid.parkitcourse.home.domain.model.Route
import com.mkiperszmid.parkitcourse.home.domain.usecase.GetPathToCarUseCase
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetPathToCarUseCaseTest {

    private lateinit var repository: HomeRepository
    private lateinit var distanceCalculator: DistanceCalculator
    private lateinit var getPathToCarUseCase: GetPathToCarUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        distanceCalculator = mockk(relaxed = true)
        getPathToCarUseCase = GetPathToCarUseCase(repository, distanceCalculator)
    }

    @Test
    fun `returns the updated current route when user is on route`() = runTest {
        val currentLocation = Location(1.0, 1.0)
        val destination = Location(20.0, 20.0)
        val polyline = listOf(
            Location(5.0, 5.0),
            Location(10.0, 10.0),
            Location(15.0, 15.0),
            Location(20.0, 20.0)
        )
        val route = Route(0, polyline)
        val totalDistance = 2000

        every { distanceCalculator.isLocationOnPath(any(), any(), any()) } returns true

        every { distanceCalculator.calculateDistance(currentLocation, polyline[0]) } returns 500f
        every { distanceCalculator.calculateDistance(currentLocation, polyline[1]) } returns 1000f
        every { distanceCalculator.calculateDistance(currentLocation, polyline[2]) } returns 1500f
        every {
            distanceCalculator.calculateDistance(
                currentLocation,
                polyline[3]
            )
        } returns totalDistance.toFloat()

        val result = getPathToCarUseCase(currentLocation, destination, route)

        assert(result.isSuccess)
        assertEquals(totalDistance, result.getOrNull()?.distance)
        assertEquals(
            listOf(
                Location(5.0, 5.0),
                Location(10.0, 10.0),
                Location(15.0, 15.0),
                Location(20.0, 20.0)
            ), result.getOrNull()?.polylines
        )
    }

    @Test
    fun `returns the updated current route when user is on route and has passed some locations`() =
        runTest {
            val currentLocation = Location(8.0, 8.0)
            val destination = Location(20.0, 20.0)
            val polyline = listOf(
                Location(5.0, 5.0),
                Location(10.0, 10.0),
                Location(15.0, 15.0),
                Location(20.0, 20.0)
            )
            val route = Route(0, polyline)
            val totalDistance = 2000

            every { distanceCalculator.isLocationOnPath(any(), any(), any()) } returns true

            every {
                distanceCalculator.calculateDistance(
                    currentLocation,
                    polyline[0]
                )
            } returns 300f
            every {
                distanceCalculator.calculateDistance(
                    currentLocation,
                    polyline[1]
                )
            } returns 200f
            every {
                distanceCalculator.calculateDistance(
                    currentLocation,
                    polyline[2]
                )
            } returns 700f
            every {
                distanceCalculator.calculateDistance(
                    currentLocation,
                    polyline[3]
                )
            } returns totalDistance.toFloat()

            val result = getPathToCarUseCase(currentLocation, destination, route)

            assert(result.isSuccess)
            assertEquals(totalDistance, result.getOrNull()?.distance)
            assertEquals(
                listOf(
                    Location(10.0, 10.0),
                    Location(15.0, 15.0),
                    Location(20.0, 20.0)
                ), result.getOrNull()?.polylines
            )
        }

    @Test
    fun `returns new recalculated route when user is not on route`() = runTest {
        val currentLocation = Location(1.0, 1.0)
        val destination = Location(20.0, 20.0)
        val route = Route(0, emptyList())

        every { distanceCalculator.isLocationOnPath(any(), any(), any()) } returns false

        getPathToCarUseCase(currentLocation, destination, route)

        coVerify(exactly = 2) { repository.getDirections(any(), any()) }
    }
}