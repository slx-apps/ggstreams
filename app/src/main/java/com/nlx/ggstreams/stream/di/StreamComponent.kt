package com.nlx.ggstreams.stream.di


import dagger.Subcomponent

//@PerScreen
@Subcomponent//(modules = [PlayerModule::class, StreamModule::class])
interface StreamComponent {

//    @Subcomponent.Factory
//    interface Factory {
//        fun create(@BindsInstance view: StreamMVP.StreamView): StreamComponent
//    }
//
//    fun inject(fragment: StreamFragment)
}