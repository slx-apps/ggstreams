package com.nlx.ggstreams.list.di

import com.nlx.ggstreams.di.PerScreen
import com.nlx.ggstreams.list.StreamListFragment
import com.nlx.ggstreams.list.mvp.StreamListMVP
import dagger.BindsInstance
import dagger.Subcomponent

@PerScreen
@Subcomponent(modules = [StreamListModule::class])
interface StreamListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance view: StreamListMVP.View): StreamListComponent
    }

    fun inject(fragment: StreamListFragment)

}