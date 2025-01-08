package com.mkiperszmid.parkitcourse.home.domain

interface FeatureFlag {
    suspend fun fetchFlags()
    fun getFlagValue(key: String): String
}