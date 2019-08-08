package com.nlx.ggstreams.di

import  com.nlx.ggstreams.App
import com.nlx.ggstreams.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@PerApp
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        StorageModule::class,
        NetworkModule::class,
        ChatModule::class,
        ApiModule::class,
        AuthModule::class,
        UtilsModule::class,
        BuildersModule::class,
        RxHelperModule::class,
        ViewModelModule::class,
        DataModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder
        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }

    fun inject(app: App)
}