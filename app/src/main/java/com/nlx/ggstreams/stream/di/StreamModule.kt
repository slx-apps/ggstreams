package com.nlx.ggstreams.stream.di

import com.nlx.ggstreams.chat.mvp.StreamChatMVP
import com.nlx.ggstreams.chat.mvp.StreamChatPresenter
import com.nlx.ggstreams.di.PerApp
import com.nlx.ggstreams.di.PerScreen
import com.nlx.ggstreams.stream.StreamFragment
import com.nlx.ggstreams.stream.mvp.BaseStreamModel
import com.nlx.ggstreams.stream.mvp.StreamMVP
import com.nlx.ggstreams.stream.mvp.StreamPresenter
import dagger.Binds
import dagger.Module


@Module
abstract class StreamModule {

    @Binds
    @PerApp
    abstract fun provideStreamView(fragment: StreamFragment) : StreamMVP.StreamView

    @Binds
    @PerApp
    abstract fun provideStreamPresenter(presenter: StreamPresenter) : StreamMVP.Presenter

    @Binds
    @PerApp
    abstract fun provideStreamChatPresenter(presenter: StreamChatPresenter) : StreamChatMVP.Presenter

    @Binds
    @PerApp
    abstract fun provideStreamModel(model: BaseStreamModel) : StreamMVP.Model

}