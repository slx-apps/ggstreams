package com.nlx.ggstreams.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.nlx.ggstreams.StreamListDeserializer
import com.nlx.ggstreams.di.PerApp
import com.nlx.ggstreams.models.EmoteIcon
import com.nlx.ggstreams.models.GGStream
import com.nlx.ggstreams.models.StreamListResponse
import com.nlx.ggstreams.rest.GGSmileDeserializer
import com.nlx.ggstreams.rest.GGStreamDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class StorageModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val ggSmilesType = object : TypeToken<Map<String, EmoteIcon>>() {}.type
        return GsonBuilder()
                .registerTypeAdapter(GGStream::class.java, GGStreamDeserializer())
                .registerTypeAdapter(ggSmilesType, GGSmileDeserializer())
                .registerTypeAdapter(StreamListResponse::class.java, StreamListDeserializer())

                .create()
    }

}