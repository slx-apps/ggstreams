package com.nlx.ggstreams.list.di

import com.nlx.ggstreams.list.StreamsDataSourceFactory
import com.nlx.ggstreams.list.data.StreamRepository
import com.nlx.ggstreams.stream.di.StreamComponent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module(
        subcomponents = [
            StreamListComponent::class
        ]
)
class StreamListModule {

    @Provides
//    @PerScreen
    fun provideStreamsDataSourceFactory(streamRepository: StreamRepository) : StreamsDataSourceFactory {
        return StreamsDataSourceFactory(streamRepository)
    }

}