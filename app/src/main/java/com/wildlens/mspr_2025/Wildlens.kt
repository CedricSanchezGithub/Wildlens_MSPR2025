package com.wildlens.mspr_2025

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Wildlens : Application() {
    override fun onCreate() {
        super.onCreate()

        // Ensure AppCompat can handle locale changes
        AppCompatDelegate.setApplicationLocales(AppCompatDelegate.getApplicationLocales())
    }
}
