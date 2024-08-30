package com.mkiperszmid.parkitcourse.home.presentation


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkiperszmid.parkitcourse.home.domain.HomeRepository
import com.mkiperszmid.parkitcourse.home.domain.LocationService
import com.mkiperszmid.parkitcourse.home.domain.model.Car
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationService: LocationService,
    private val repository: HomeRepository
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

        viewModelScope.launch {
            repository.getParkedCar()?.let {
                state = state.copy(
                    car = it,
                    carStatus = CarStatus.PARKED_CAR
                )
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.SaveCar -> {
                viewModelScope.launch {
                    val currentLocation = locationService.getCurrentLocation()
                    state = state.copy(
                        currentLocation = currentLocation
                    )
                    currentLocation?.let {
                        val car = Car(latitude = it.latitude, longitude = it.longitude)
                        repository.parkCar(car)
                        val parkedCar = repository.getParkedCar()
                        state = state.copy(
                            car = parkedCar,
                            carStatus = CarStatus.PARKED_CAR
                        )
                    }
                }
            }

            HomeEvent.StartSearch -> {
                state = state.copy(
                    carStatus = CarStatus.SEARCHING
                )
            }

            HomeEvent.StopSearch -> {
                state.car?.let {
                    viewModelScope.launch {
                        repository.deleteCar(it)
                    }
                }
                state = state.copy(
                    carStatus = CarStatus.NO_PARKED_CAR,
                    car = null
                )
            }
        }
    }
}
