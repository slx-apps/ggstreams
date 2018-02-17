package com.nlx.ggstreams.di.modules

import com.nlx.ggstreams.MainActivity
import com.nlx.ggstreams.auth.AuthActivity
import com.nlx.ggstreams.auth.login.LoginFragment
import com.nlx.ggstreams.auth.UserFragment
import com.nlx.ggstreams.auth.login.di.LoginModule
import com.nlx.ggstreams.auth.user.di.UserModule
import com.nlx.ggstreams.di.PerScreen
import com.nlx.ggstreams.list.StreamListFragment
import com.nlx.ggstreams.list.di.StreamListModule
import com.nlx.ggstreams.stream.StreamFragment
import com.nlx.ggstreams.stream.di.PlayerModule
import com.nlx.ggstreams.stream.di.StreamModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {


    @ContributesAndroidInjector(modules = arrayOf())
    abstract fun contributeAuthActivityInjector(): AuthActivity

    @ContributesAndroidInjector(modules = [(LoginModule::class)])
    @PerScreen
    abstract fun contributeLoginFragmentInjector(): LoginFragment

    @ContributesAndroidInjector(modules = [(UserModule::class)])
    @PerScreen
    abstract fun contributeUserFragmentInjector(): UserFragment

    @ContributesAndroidInjector(modules = arrayOf())
    abstract fun contributeMainActivityInjector(): MainActivity

    @ContributesAndroidInjector(modules = [(StreamListModule::class)])
    @PerScreen
    abstract fun contributeStreamListInjector(): StreamListFragment

    @ContributesAndroidInjector(modules = [(StreamModule::class), (PlayerModule::class)])
    @PerScreen
    abstract fun contributeStreamFragmentInjector(): StreamFragment




}