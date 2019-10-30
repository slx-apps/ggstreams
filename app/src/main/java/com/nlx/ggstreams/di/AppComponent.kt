package com.nlx.ggstreams.di

import android.content.Context
import com.nlx.ggstreams.MainActivity
import com.nlx.ggstreams.auth.di.UserSubComponent
import com.nlx.ggstreams.di.modules.*
import com.nlx.ggstreams.list.di.StreamListComponent
import com.nlx.ggstreams.stream.di.StreamComponent
import dagger.BindsInstance
import dagger.Component

@PerApp
@Component(modules = [
    AppModule::class,
    StorageModule::class,
    NetworkModule::class,
    ChatModule::class,
    ApiModule::class,
    AuthModule::class,
    UtilsModule::class,
    RxHelperModule::class,
    ViewModelModule::class,
    DataModule::class,
    AppSubcomponents::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun userComponent(): UserSubComponent.Factory
    fun streamListComponent(): StreamListComponent.Factory
    fun streamComponent(): StreamComponent.Factory

    fun inject(activity: MainActivity)
}