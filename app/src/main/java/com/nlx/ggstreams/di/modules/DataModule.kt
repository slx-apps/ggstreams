package com.nlx.ggstreams.di.modules

import com.nlx.ggstreams.di.PerApp
import com.nlx.ggstreams.list.data.StreamListRepository
import com.nlx.ggstreams.list.data.StreamRepository
import com.nlx.ggstreams.rest.GGApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideStreamRepository(api: GGApi): StreamRepository {
        return StreamListRepository(api)
    }

}