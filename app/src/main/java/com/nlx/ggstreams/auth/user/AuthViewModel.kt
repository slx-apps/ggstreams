package com.nlx.ggstreams.auth.user

import androidx.lifecycle.ViewModel
import com.nlx.ggstreams.auth.AuthManager
import com.nlx.ggstreams.data.PreferencesUtils
import com.nlx.ggstreams.models.ChatProfile
import com.nlx.ggstreams.utils.rx.RxUtils
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val preferencesUtils: PreferencesUtils,
                                        private val model: AuthManager,
                                        private val rxUtils: RxUtils) : ViewModel() {

    fun logout() {

    }

    var compositeDisposal =  CompositeDisposable()
    private var profile =  ChatProfile()

    fun getProfile(): ChatProfile = profile

    fun chatLogin(email: String, password: String) {
        compositeDisposal.add(
                model.chatLogin(email, password)
                        .subscribeOn(rxUtils.subscribeScheduler)
                        .observeOn(rxUtils.observeScheduler)
                        .subscribe({
                            preferencesUtils.saveProfile(it)
                            //view.userLoggedIn()
                        }, {
                            //view.handleErrors(it)
                        })
        )
    }

    fun isValid(login: CharSequence, pass: CharSequence): Boolean {
        return (login.isNotEmpty() && pass.isNotEmpty())
    }
}