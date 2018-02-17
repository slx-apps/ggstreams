package com.nlx.ggstreams.auth.user.mvp

import com.nlx.ggstreams.auth.login.di.AuthManager
import com.nlx.ggstreams.utils.rx.RxUtils
import com.nlx.ggstreams.models.ChatProfile
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class UserProfilePresenter @Inject constructor(private val view: UserProfileMVP.View,
                                               private val model: AuthManager,
                                               private val rxUtils: RxUtils) : UserProfileMVP.Presenter {

    override var compositeDisposal = CompositeDisposable()

    override fun logout() {

    }

    override fun getProfile(): ChatProfile? = null
}