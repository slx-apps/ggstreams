package com.nlx.ggstreams

import android.app.Application
import com.nlx.ggstreams.di.AppComponent
import com.nlx.ggstreams.di.DaggerAppComponent
import com.nlx.ggstreams.di.modules.AppModule

class App : Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

    }

}