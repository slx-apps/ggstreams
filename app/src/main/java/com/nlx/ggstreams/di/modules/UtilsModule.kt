package com.nlx.ggstreams.di.modules

import android.app.Application
import android.content.Context
import com.nlx.ggstreams.data.PreferencesUtils
import com.nlx.ggstreams.di.PerApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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