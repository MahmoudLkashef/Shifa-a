package com.syncdev.shifaa

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShifaaApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}