package com.mkiperszmid.parkitcourse

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork() // or .detectAll() for all detectable problems
                    .penaltyLog() // Log violations to logcat
                    // .penaltyDeath() // Optional: Crash on violation (for stricter testing)
                    // .penaltyDialog() // Optional: Show a dialog (can be intrusive)
                    // .penaltyFlashScreen() // Optional: Flash screen on violation
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    // .penaltyDeath() // Optional: Crash on violation
                    .build()
            )
        }
    }
}