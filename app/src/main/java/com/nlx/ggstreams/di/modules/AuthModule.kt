package com.nlx.ggstreams.di.modules

import com.nlx.ggstreams.auth.login.di.AuthManager
import com.nlx.ggstreams.data.PreferencesUtils
import com.nlx.ggstreams.di.PerApp
import com.nlx.ggstreams.rest.GGV1Api
import dagger.Module
import dagger.Provides

@Module
class AuthModule {

    @Provides
    @PerApp
    fun provideAuthManager(api: GGV1Api, utils: PreferencesUtils): AuthManager {
        return AuthManager(api, utils)
    }

}
