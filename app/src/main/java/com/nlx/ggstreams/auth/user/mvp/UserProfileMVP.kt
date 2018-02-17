package com.nlx.ggstreams.auth.user.mvp

import com.nlx.ggstreams.models.ChatProfile
import com.nlx.ggstreams.mvp.BasePresenter
import com.nlx.ggstreams.mvp.BaseView


interface UserProfileMVP {

    interface Model {

    }

    interface Presenter: BasePresenter {
        fun logout()
        fun getProfile() : ChatProfile?
    }

    interface View: BaseView {

    }

}