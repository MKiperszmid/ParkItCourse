package com.mkiperszmid.parkitcourse.home.presentation


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkiperszmid.parkitcourse.home.domain.LocationService
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationService: LocationService
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    init {
        viewModelScope.launch {
            locationService.getCurrentLocation()?.let {
                state = state.copy(
                    currentLocation = it
                )
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.SaveCar -> TODO()
            HomeEvent.StartSearch -> TODO()
            HomeEvent.StopSearch -> TODO()
        }
    }
}
