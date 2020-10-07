package com.nlx.ggstreams.list.di

import com.nlx.ggstreams.list.StreamsDataSourceFactory
import com.nlx.ggstreams.list.data.StreamRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class StreamListModule {

    @Provides
    fun provideStreamsDataSourceFactory(streamRepository: StreamRepository) : StreamsDataSourceFactory {
        return StreamsDataSourceFactory(streamRepository)
    }

}