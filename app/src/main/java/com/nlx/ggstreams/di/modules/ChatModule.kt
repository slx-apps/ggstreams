package com.nlx.ggstreams.di.modules

import com.google.gson.Gson
import com.nlx.ggstreams.chat.GGChat
import com.nlx.ggstreams.rest.GGApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class ChatModule {

    @Provides
    @Singleton
    fun provideGGChat(okHttpClient: OkHttpClient, api: GGApi, gson: Gson): GGChat {
        return GGChat(okHttpClient, api, gson)
    }

}
