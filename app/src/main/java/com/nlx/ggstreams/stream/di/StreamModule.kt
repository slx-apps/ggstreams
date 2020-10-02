package com.nlx.ggstreams.stream.di

import com.nlx.ggstreams.chat.mvp.StreamChatMVP
import com.nlx.ggstreams.chat.mvp.StreamChatPresenter
import com.nlx.ggstreams.stream.mvp.BaseStreamModel
import com.nlx.ggstreams.stream.mvp.StreamMVP
import com.nlx.ggstreams.stream.mvp.StreamPresenter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@InstallIn(ApplicationComponent::class)
@Module
abstract class StreamModule {

    @Binds
//    @PerScreen
    abstract fun provideStreamPresenter(presenter: StreamPresenter) : StreamMVP.Presenter

    @Binds
//    @PerScreen
    abstract fun provideStreamChatPresenter(presenter: StreamChatPresenter) : StreamChatMVP.Presenter

    @Binds
//    @PerScreen
    abstract fun provideStreamModel(model: BaseStreamModel) : StreamMVP.Model

}