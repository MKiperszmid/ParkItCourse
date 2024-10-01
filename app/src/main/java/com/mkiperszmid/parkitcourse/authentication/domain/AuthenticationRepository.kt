package com.mkiperszmid.parkitcourse.authentication.domain

import android.content.Context

interface AuthenticationRepository {
    suspend fun oneTapLogin(context: Context): Result<Unit>
    fun isLoggedIn(): Boolean
}