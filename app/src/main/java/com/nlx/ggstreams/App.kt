package com.nlx.ggstreams

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

//    val appComponent: AppComponent by lazy {
//        DaggerAppComponent.factory().create(applicationContext)
//    }

    override fun onCreate() {
        super.onCreate()
    }

}