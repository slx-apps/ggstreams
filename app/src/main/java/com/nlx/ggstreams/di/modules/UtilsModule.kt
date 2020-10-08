package com.nlx.ggstreams.di.modules

import android.app.Application
import com.nlx.ggstreams.data.PreferencesUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class UtilsModule {
    
    @Provides
    @Singleton
    fun providePreferencesUtils(context: Application): PreferencesUtils {
        return PreferencesUtils(context)
    }

}