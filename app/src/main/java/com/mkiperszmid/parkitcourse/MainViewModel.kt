package com.mkiperszmid.parkitcourse

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mkiperszmid.parkitcourse.authentication.domain.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository) : ViewModel() {
    val isLoggedIn = authenticationRepository.isLoggedIn()
}