package com.mkiperszmid.parkitcourse.authentication.presentation


data class LoginState(
    val loginStatus: LoginStatus = LoginStatus.IDLE
)

enum class LoginStatus {
    IDLE, LOADING, LOGGED_IN
}