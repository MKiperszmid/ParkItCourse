package com.mkiperszmid.parkitcourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.mkiperszmid.parkitcourse.navigation.NavigationHost
import com.mkiperszmid.parkitcourse.navigation.NavigationRoute
import com.mkiperszmid.parkitcourse.ui.theme.ParkItCourseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewmodel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParkItCourseTheme {
                val navHostController = rememberNavController()
                val startDestination = getStartDestination()
                //asdasd
                NavigationHost(
                    navHostController = navHostController,
                    startDestination = startDestination
                )
            }
        }
    }

    private fun getStartDestination(): NavigationRoute {
        return if (viewmodel.isLoggedIn) NavigationRoute.HomeScreen else NavigationRoute.LoginScreen
    }
}
