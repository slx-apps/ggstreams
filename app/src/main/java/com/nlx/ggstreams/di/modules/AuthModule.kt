package com.nlx.ggstreams.di.modules

import com.nlx.ggstreams.ui.auth.AuthManager
import com.nlx.ggstreams.data.PreferencesUtils
import com.nlx.ggstreams.rest.GGV1Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class AuthModule {

    @Provides
    @Singleton
    fun provideAuthManager(api: GGV1Api, utils: PreferencesUtils): AuthManager {
        return AuthManager(api, utils)
    }

}
