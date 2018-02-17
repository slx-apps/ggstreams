package com.nlx.ggstreams.auth.user.di

import com.nlx.ggstreams.auth.UserFragment
import com.nlx.ggstreams.auth.login.di.AuthManager
import com.nlx.ggstreams.auth.user.mvp.UserProfileMVP
import com.nlx.ggstreams.auth.user.mvp.UserProfilePresenter
import com.nlx.ggstreams.di.PerApp
import com.nlx.ggstreams.di.PerScreen
import dagger.Binds
import dagger.Module

@Module
abstract class UserModule {

    @Binds
    @PerScreen
    abstract fun provideUserProfileView(fragment: UserFragment) : UserProfileMVP.View

    @Binds
    @PerScreen
    abstract fun provideUserProfilePresenter(presenter: UserProfilePresenter) : UserProfileMVP.Presenter

}