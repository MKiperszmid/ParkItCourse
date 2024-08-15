package com.mkiperszmid.parkitcourse.authentication.domain

interface AuthenticationRepository {
    suspend fun oneTapLogin(): Result<Unit>
    fun isLoggedIn(): Boolean
}