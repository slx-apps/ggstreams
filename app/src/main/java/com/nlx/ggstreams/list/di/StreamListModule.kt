package com.nlx.ggstreams.list.di

import com.nlx.ggstreams.di.PerApp
import com.nlx.ggstreams.di.PerScreen
import com.nlx.ggstreams.list.StreamListFragment
import com.nlx.ggstreams.list.mvp.StreamListMVP
import com.nlx.ggstreams.list.mvp.StreamListModel
import com.nlx.ggstreams.list.mvp.StreamListPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class StreamListModule {

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

*/

    @Binds
    @PerApp
    abstract fun provideView(fragment: StreamListFragment) : StreamListMVP.View

    @Binds
    @PerApp
    abstract fun provideStreamListPresenter(presenter: StreamListPresenter) : StreamListMVP.Presenter

    @Binds
    @PerApp
    abstract fun provideStreamListModel(model: StreamListModel) : StreamListMVP.Model

}