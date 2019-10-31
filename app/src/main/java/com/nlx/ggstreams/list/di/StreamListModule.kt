package com.nlx.ggstreams.list.di

import com.nlx.ggstreams.di.PerScreen
import com.nlx.ggstreams.list.StreamsDataSourceFactory
import com.nlx.ggstreams.list.StreamsPageKeyedDataSource
import com.nlx.ggstreams.list.data.StreamRepository
import dagger.Module
import dagger.Provides

@Module
class StreamListModule {

/*
    @Provides
    @PerScreen
    fun provideFragment(fragment: StreamListFragment) : StreamListMVP.View = fragment

    @Provides
    @PerScreen
    fun provideStreamListPresenter(view: StreamListMVP.View, model: StreamListMVP.Model, rxUtils: RxUtils) : StreamListMVP.Presenter
            = StreamListPresenter(view, model, rxUtils)

    @Provides
    @PerScreen
    fun provideStreamListModel(api: GGApi) : StreamListMVP.Model
            = StreamListModel(api)

    @Binds
    @PerScreen
    abstract fun provideView(fragment: StreamListFragment) : StreamListMVP.View

    @Binds
    @PerScreen
    abstract fun provideStreamListPresenter(presenter: StreamListPresenter) : StreamListMVP.Presenter


 @Provides
    @PerScreen
    fun provideStreamsPageKeyedDataSource(streamRepository: StreamRepository) : StreamsPageKeyedDataSource
            = StreamsPageKeyedDataSource(streamRepository)
*/




    @Provides
    @PerScreen
    fun provideStreamsDataSourceFactory(streamRepository: StreamRepository) : StreamsDataSourceFactory {
        return StreamsDataSourceFactory(streamRepository)
    }

}