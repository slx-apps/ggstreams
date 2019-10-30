package com.nlx.ggstreams

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.nlx.ggstreams.di.DaggerAppComponent
import com.nlx.ggstreams.di.modules.AppModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .application(this)
                .appModule(AppModule(this))
                .build()
                .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingInjector
    }
}