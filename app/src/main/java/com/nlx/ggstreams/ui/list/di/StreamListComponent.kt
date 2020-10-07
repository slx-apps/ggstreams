package com.nlx.ggstreams.ui.list.di

import com.nlx.ggstreams.ui.list.StreamListFragment
import dagger.BindsInstance
import dagger.Subcomponent

//@PerScreen
@Subcomponent//(modules = [StreamListModule::class])
interface StreamListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): StreamListComponent
    }
//
//    fun inject(fragment: StreamListFragment)

}