package com.nlx.ggstreams.di.modules

import com.google.gson.Gson
import com.nlx.ggstreams.chat.GGChat
import com.nlx.ggstreams.auth.AuthManager
import com.nlx.ggstreams.di.PerApp
import com.nlx.ggstreams.rest.GGApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class ChatModule {

    @Provides
    @PerApp
    fun provideGGChat(okHttpClient: OkHttpClient, api: GGApi, gson: Gson, authManager: AuthManager): GGChat {
        return GGChat(okHttpClient, api, gson, authManager)
    }

}
