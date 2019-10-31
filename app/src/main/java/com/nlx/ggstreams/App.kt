package com.nlx.ggstreams

import android.app.Application
import com.nlx.ggstreams.di.AppComponent
import com.nlx.ggstreams.di.DaggerAppComponent
import com.nlx.ggstreams.di.modules.AppModule

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
    }

}