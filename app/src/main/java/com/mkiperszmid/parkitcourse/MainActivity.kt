package com.mkiperszmid.parkitcourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.mkiperszmid.parkitcourse.navigation.NavigationHost
import com.mkiperszmid.parkitcourse.navigation.NavigationRoute
import com.mkiperszmid.parkitcourse.ui.theme.ParkItCourseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParkItCourseTheme {
                val navHostController = rememberNavController()
                val startDestination = NavigationRoute.LoginScreen
                NavigationHost(
                    navHostController = navHostController,
                    startDestination = startDestination
                )
            }
        }
    }
}
