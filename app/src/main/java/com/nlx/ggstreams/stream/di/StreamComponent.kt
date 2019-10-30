package com.nlx.ggstreams.stream.di

import com.nlx.ggstreams.di.PerScreen
import com.nlx.ggstreams.stream.StreamFragment
import com.nlx.ggstreams.stream.mvp.StreamMVP
import dagger.BindsInstance
import dagger.Subcomponent

@PerScreen
@Subcomponent(modules = [PlayerModule::class, StreamModule::class])
interface StreamComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance view: StreamMVP.StreamView): StreamComponent
    }

    fun inject(fragment: StreamFragment)
}