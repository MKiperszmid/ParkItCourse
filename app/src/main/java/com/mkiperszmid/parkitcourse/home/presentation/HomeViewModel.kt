package com.mkiperszmid.parkitcourse.home.presentation


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkiperszmid.parkitcourse.home.domain.FeatureFlag
import com.mkiperszmid.parkitcourse.home.domain.HomeRepository
import com.mkiperszmid.parkitcourse.home.domain.LocationService
import com.mkiperszmid.parkitcourse.home.domain.model.Car
import com.mkiperszmid.parkitcourse.home.domain.model.Location
import com.mkiperszmid.parkitcourse.home.domain.usecase.GetPathToCarUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationService: LocationService,
    private val repository: HomeRepository,
    private val getPathToCarUseCase: GetPathToCarUseCase,
    private val featureFlag: FeatureFlag
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    private lateinit var locationJob: Job

    init {
        getCurrentLocation()
        viewModelScope.launch {
            repository.getParkedCar()?.let {
                state = state.copy(
                    car = it,
                    carStatus = CarStatus.PARKED_CAR
                )
            }
        }

        println("El valor de la api key es: ${featureFlag.getFlagValue("openai_apikey")}")
    }

    private fun getCurrentLocation() {
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
            HomeEvent.SaveCar -> {
                viewModelScope.launch {
                    val currentLocation = locationService.getCurrentLocation()
                    state = state.copy(
                        currentLocation = currentLocation
                    )
                    currentLocation?.let {
                        val car = Car(location = it)
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
                state.car?.let { carLocation ->
                    locationJob = viewModelScope.launch {
                        val currentLocation = locationService.getCurrentLocation()
                        state = state.copy(
                            currentLocation = currentLocation
                        )
                        state.currentLocation?.let { userLocation ->
                            repository.getDirections(
                                userLocation,
                                carLocation.location
                            ).onSuccess {
                                state = state.copy(
                                    route = it,
                                    carStatus = CarStatus.SEARCHING
                                )

                                locationService.getLocationUpdates().collectLatest {
                                    state = state.copy(
                                        currentLocation = it
                                    )
                                    if (state.currentLocation != null && state.route != null) {
                                        getPathToCarUseCase(
                                            state.currentLocation!!,
                                            carLocation.location,
                                            state.route!!
                                        ).onSuccess {
                                            state = state.copy(
                                                route = it
                                            )
                                        }.onFailure {
                                            println("Failure!")
                                        }
                                    }
                                    println("Ubicacion: $it")
                                }
                            }.onFailure {
                                // TODO: Show error message
                            }
                        }
                    }
                }
            }

            HomeEvent.StopSearch -> {
                state.car?.let {
                    viewModelScope.launch {
                        repository.deleteCar(it)
                    }
                }
                state = state.copy(
                    carStatus = CarStatus.NO_PARKED_CAR,
                    car = null,
                    route = null
                )
                locationJob.cancel()
            }

            is HomeEvent.PermissionResult -> {
                state = state.copy(
                    hasRequiredPermissions = event.permissionsGranted
                )
                if (event.permissionsGranted) {
                    getCurrentLocation()
                }
            }
        }
    }
}
