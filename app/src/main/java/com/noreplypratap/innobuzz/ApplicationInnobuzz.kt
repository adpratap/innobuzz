package com.noreplypratap.innobuzz

import android.app.Application
import com.noreplypratap.innobuzz.utils.startTrackerService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationInnobuzz: Application() {
    override fun onCreate() {
        super.onCreate()
        startTrackerService()
    }
}