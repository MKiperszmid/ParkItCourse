package com.mkiperszmid.parkitcourse.home.presentation


sealed interface HomeEvent {
    data object SaveCar : HomeEvent
    data object StartSearch : HomeEvent
    data object StopSearch : HomeEvent
    data class PermissionResult(val permissionsGranted: Boolean) : HomeEvent
}
