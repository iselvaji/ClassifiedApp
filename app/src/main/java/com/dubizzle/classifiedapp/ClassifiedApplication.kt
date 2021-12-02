package com.dubizzle.classifiedapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ClassifiedApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Todo - Initialize ANRWatchDog()

        // Todo - Initialize Steho for API debugging

        // Todo - Initialize Firebase crashlytics

        // Todo - Initialize logger framework [ex. Timber]

        // Todo - Implement GeneralErrorHandler

    }
}