package com.mkiperszmid.parkitcourse

import android.app.Application
import com.mkiperszmid.parkitcourse.home.domain.FeatureFlag
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {
    @Inject
    lateinit var featureFlag: FeatureFlag
    override fun onCreate() {
        super.onCreate()
        GlobalScope.launch {
            featureFlag.fetchFlags()
        }
    }
}