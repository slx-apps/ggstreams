package com.nlx.ggstreams.auth.login.mvp

import com.nlx.ggstreams.auth.login.di.AuthManager
import com.nlx.ggstreams.data.PreferencesUtils
import com.nlx.ggstreams.models.ChatProfile
import com.nlx.ggstreams.utils.rx.RxUtils
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginPresenter @Inject constructor(private val preferencesUtils: PreferencesUtils,
                                         private val view: LoginMVP.View,
                                         private val model: AuthManager,
                                         private val rxUtils: RxUtils): LoginMVP.Presenter {

    override var compositeDisposal =  CompositeDisposable()
    private var profile =  ChatProfile()

    override fun getProfile(): ChatProfile = profile

    override fun chatLogin(email: String, password: String) {
        compositeDisposal.add(
        model.chatLogin(email, password)
                .subscribeOn(rxUtils.subscribeScheduler)
                .observeOn(rxUtils.observeScheduler)
                .subscribe({
                    preferencesUtils.saveProfile(it)
                    view.userLoggedIn()
                }, {
                    view.handleErrors(it)
                })
        )
    }

    override fun isValid(login: CharSequence, pass: CharSequence): Boolean {
        return (login.isNotEmpty() && pass.isNotEmpty())
    }

}