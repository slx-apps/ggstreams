package com.nlx.ggstreams.auth.login.di

import com.nlx.ggstreams.auth.login.LoginFragment
import com.nlx.ggstreams.auth.login.mvp.LoginMVP
import com.nlx.ggstreams.auth.login.mvp.LoginPresenter
import com.nlx.ggstreams.auth.user.mvp.UserProfileMVP
import com.nlx.ggstreams.di.PerApp
import com.nlx.ggstreams.di.PerScreen
import dagger.Binds
import dagger.Module

@Module
abstract class LoginModule {

    @Binds
    @PerScreen
    abstract fun provideView(fragment: LoginFragment) : LoginMVP.View

    @Binds
    @PerScreen
    abstract fun provideLoginPresenter(presenter: LoginPresenter) : LoginMVP.Presenter
}