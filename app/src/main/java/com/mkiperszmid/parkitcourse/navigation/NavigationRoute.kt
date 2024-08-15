package com.mkiperszmid.parkitcourse.navigation

sealed class NavigationRoute(val route: String) {
    data object LoginScreen : NavigationRoute("login")
    data object HomeScreen : NavigationRoute("home")
}