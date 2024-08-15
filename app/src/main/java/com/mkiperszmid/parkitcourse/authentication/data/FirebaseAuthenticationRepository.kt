package com.mkiperszmid.parkitcourse.authentication.data

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mkiperszmid.parkitcourse.BuildConfig
import com.mkiperszmid.parkitcourse.authentication.domain.AuthenticationRepository
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID

class FirebaseAuthenticationRepository(
    private val context: Context
) : AuthenticationRepository {
    override suspend fun oneTapLogin(): Result<Unit> {
        val credentialManager = CredentialManager.create(context)
        val nonce = UUID.randomUUID().toString().toByteArray()
        val digest = MessageDigest.getInstance("SHA-256").digest(nonce)
        val encryptNonce = digest.joinToString("") { "%02x".format(it) }
        val googleOptions = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(true)
            .setNonce(encryptNonce)
            .setServerClientId(BuildConfig.GOOGLE_WEBCLIENT_ID).build()
        val request = GetCredentialRequest.Builder().addCredentialOption(googleOptions).build()
        return try {
            val result = credentialManager.getCredential(context, request).credential
            val googleIdToken = GoogleIdTokenCredential.createFrom(result.data).idToken
            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)

            val firebaseResult = Firebase.auth.signInWithCredential(firebaseCredential).await()
            if (firebaseResult.user == null) {
                return Result.failure(Exception("Failed to sign in, please try again later"))
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun isLoggedIn(): Boolean {
        return Firebase.auth.currentUser != null
    }
}