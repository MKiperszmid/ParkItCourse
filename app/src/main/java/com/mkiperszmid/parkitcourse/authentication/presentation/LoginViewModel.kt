package com.mkiperszmid.parkitcourse.authentication.presentation


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkiperszmid.parkitcourse.authentication.domain.AuthenticationRepository
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.LogIn -> {
                viewModelScope.launch {
                    authenticationRepository.oneTapLogin().onSuccess {
                        state = state.copy(
                            loginStatus = LoginStatus.LOGGED_IN
                        )
                    }.onFailure {
                        state = state.copy(
                            loginStatus = LoginStatus.IDLE
                        )
                        // TODO: Mostrar error
                    }
                }
            }
        }
    }
}
