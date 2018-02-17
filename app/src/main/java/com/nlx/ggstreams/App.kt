package com.nlx.ggstreams

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.nlx.ggstreams.di.DaggerAppComponent
import com.nlx.ggstreams.di.modules.AppModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector, HasSupportFragmentInjector {


    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var dispatchingSupportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .application(this)
                .appModule(AppModule(this))
                .build()
                .inject(this)
    }


    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingSupportFragmentInjector
    }

}