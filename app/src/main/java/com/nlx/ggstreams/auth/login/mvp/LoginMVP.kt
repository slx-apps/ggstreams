package com.nlx.ggstreams.auth.login.mvp

import com.nlx.ggstreams.models.ChatProfile
import com.nlx.ggstreams.mvp.BasePresenter
import com.nlx.ggstreams.mvp.BaseView


interface LoginMVP {

    interface Presenter: BasePresenter {
        fun chatLogin(email: String, password: String)
        fun isValid(login: CharSequence, pass: CharSequence): Boolean
        fun getProfile(): ChatProfile
    }

    interface View: BaseView {
        fun userLoggedIn()
    }

}