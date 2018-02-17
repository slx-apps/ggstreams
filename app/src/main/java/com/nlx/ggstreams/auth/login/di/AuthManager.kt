package com.nlx.ggstreams.auth.login.di

import com.jakewharton.rxrelay2.PublishRelay
import com.nlx.ggstreams.auth.user.mvp.UserProfileMVP
import com.nlx.ggstreams.data.PreferencesUtils
import com.nlx.ggstreams.models.AuthResponse
import com.nlx.ggstreams.models.ChatProfile
import com.nlx.ggstreams.rest.GGV1Api
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthManager @Inject constructor(private val api: GGV1Api, private val utils: PreferencesUtils): UserProfileMVP.Model {
    private val profileRelay = PublishRelay.create<ChatProfile>()

    var profile: ChatProfile = utils.loadProfile()

    fun login(login: String, password: String): Observable<AuthResponse> {
        return api.login(login, password)
    }

    fun chatLogin(login: String, password: String): Observable<ChatProfile> {
        return api.chatLogin(login, password)
                .subscribeOn(Schedulers.io())
                .doOnNext { chatProfile ->
                    profile = chatProfile
                    profile.login = login
                    saveProfile(profile)
                }
    }


    fun profileObservable(): Flowable<ChatProfile> {
        return profileRelay.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun saveProfile(profile: ChatProfile) {
        utils.saveProfile(profile)
    }

    fun logout() {
        utils.clearUser()
        profile = ChatProfile()
        profileRelay.accept(profile)
    }

}
