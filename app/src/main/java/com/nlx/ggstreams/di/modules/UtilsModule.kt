package com.nlx.ggstreams.di.modules

import android.content.Context
import com.nlx.ggstreams.data.PreferencesUtils
import com.nlx.ggstreams.di.PerApp
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {
    
    @Provides
    @PerApp
    fun providePreferencesUtils(context: Context): PreferencesUtils {
        return PreferencesUtils(context)
    }

}