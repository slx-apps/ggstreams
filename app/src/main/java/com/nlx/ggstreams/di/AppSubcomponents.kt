package com.nlx.ggstreams.di

import com.nlx.ggstreams.auth.di.UserSubComponent
import com.nlx.ggstreams.list.di.StreamListComponent
import com.nlx.ggstreams.stream.di.StreamComponent
import dagger.Module

@Module(subcomponents = [UserSubComponent::class, StreamListComponent::class, StreamComponent::class])
class AppSubcomponents {
}