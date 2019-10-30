package com.nlx.ggstreams.auth.di

import com.nlx.ggstreams.auth.AuthActivity
import com.nlx.ggstreams.auth.login.LoginFragment
import com.nlx.ggstreams.auth.user.UserFragment
import dagger.Subcomponent

@Subcomponent(modules = [UserModule::class])
interface UserSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserSubComponent
    }

    fun inject(activity: AuthActivity)
    fun inject(fragment: UserFragment)
    fun inject(fragment: LoginFragment)
}
