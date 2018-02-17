package com.nlx.ggstreams.di.modules

import com.google.gson.Gson
import com.nlx.ggstreams.data.EmoteIconsRepo
import com.nlx.ggstreams.data.PreferencesUtils
import com.nlx.ggstreams.list.mvp.StreamListModel
import com.nlx.ggstreams.di.PerApp
import com.nlx.ggstreams.rest.GGApi
import com.nlx.ggstreams.rest.GGRestClient
import com.nlx.ggstreams.rest.GGV1Api
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    @Provides
    @PerApp
    fun provideEmoteIconsRepo(api: GGApi, utils: PreferencesUtils): EmoteIconsRepo {
        return EmoteIconsRepo(api, utils)
    }

    @Provides
    @PerApp
    fun provideStreamRepo(api: GGApi): StreamListModel {
        return StreamListModel(api)
    }

    @Provides
    @PerApp
    fun provideConverter(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @PerApp
    fun provideCallAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @PerApp
    fun provideGGRestClient(client: OkHttpClient,
                            convFactory: Converter.Factory,
                            callAdapterFactory: CallAdapter.Factory): GGRestClient {
        return GGRestClient(client, convFactory, callAdapterFactory)
    }

    @Provides
    @PerApp
    fun provideApi(client: GGRestClient): GGApi {
        return client.apiService
    }

    @Provides
    @PerApp
    fun provideApiV1(client: GGRestClient): GGV1Api {
        return client.apiV1Service
    }
}