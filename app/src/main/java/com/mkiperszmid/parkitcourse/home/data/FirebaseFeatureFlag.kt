package com.mkiperszmid.parkitcourse.home.data

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.firebase.remoteconfig.remoteConfig
import com.mkiperszmid.parkitcourse.home.domain.FeatureFlag
import kotlinx.coroutines.tasks.await

class FirebaseFeatureFlag : FeatureFlag {
    private val firebaseConfig = Firebase.remoteConfig

    override suspend fun fetchFlags() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 10
        }
        firebaseConfig.setConfigSettingsAsync(configSettings)
        firebaseConfig.fetchAndActivate().await()
    }

    override fun getFlagValue(key: String): String {
        return firebaseConfig.getValue(key).asString()
    }
}