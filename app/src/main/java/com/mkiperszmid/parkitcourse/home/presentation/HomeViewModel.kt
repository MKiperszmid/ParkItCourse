package com.mkiperszmid.parkitcourse.home.presentation


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.SaveCar -> TODO()
            HomeEvent.StartSearch -> TODO()
            HomeEvent.StopSearch -> TODO()
        }
    }
}
