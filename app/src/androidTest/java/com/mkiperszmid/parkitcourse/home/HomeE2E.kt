package com.mkiperszmid.parkitcourse.home

import android.Manifest
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.mkiperszmid.parkitcourse.MainActivity
import com.mkiperszmid.parkitcourse.home.data.HomeRepositoryImpl
import com.mkiperszmid.parkitcourse.home.data.LocationServiceImpl
import com.mkiperszmid.parkitcourse.home.data.distance.DistanceCalculatorImpl
import com.mkiperszmid.parkitcourse.home.domain.HomeRepository
import com.mkiperszmid.parkitcourse.home.domain.LocationService
import com.mkiperszmid.parkitcourse.home.domain.usecase.GetPathToCarUseCase
import com.mkiperszmid.parkitcourse.home.presentation.CarStatus
import com.mkiperszmid.parkitcourse.home.presentation.HomeScreen
import com.mkiperszmid.parkitcourse.home.presentation.HomeViewModel
import com.mkiperszmid.parkitcourse.navigation.NavigationRoute
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeE2E {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val locationPermission = GrantPermissionRule.grant(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private lateinit var homeViewmodel: HomeViewModel
    private lateinit var navController: NavHostController

    @Before
    fun setup() {
        val homeRepository = FakeHomeRepository()
        val distanceCalculator = DistanceCalculatorImpl()
        homeViewmodel = HomeViewModel(
            FakeLocationService(),
            homeRepository,
            GetPathToCarUseCase(homeRepository, distanceCalculator)
        )

        composeRule.activity.setContent {
            navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = NavigationRoute.HomeScreen.route
            ) {
                composable(NavigationRoute.HomeScreen.route) {
                    HomeScreen(homeViewmodel)
                }
            }
        }
    }

    @Test
    fun parkCar() {
        var state = homeViewmodel.state
        assert(state.car == null)
        assert(state.route == null)
        assert(state.carStatus == CarStatus.NO_PARKED_CAR)
        composeRule.onNodeWithText("Park Here").assertIsDisplayed().performClick()
        state = homeViewmodel.state
        assert(state.car != null)
        assert(state.route == null)
        assert(state.carStatus == CarStatus.PARKED_CAR)
        composeRule.onNodeWithText("Get Directions").assertIsDisplayed().performClick()
        state = homeViewmodel.state
        assert(state.car != null)
        assert(state.route != null)
        assert(state.carStatus == CarStatus.SEARCHING)
        composeRule.onNodeWithText("Stop Searching").assertIsDisplayed().performClick()
        state = homeViewmodel.state
        assert(state.car == null)
        assert(state.route == null)
        assert(state.carStatus != CarStatus.NO_PARKED_CAR)
    }
}