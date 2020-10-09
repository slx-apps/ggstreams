package com.nlx.ggstreams.rest


import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class GGRestClient(client: OkHttpClient,
                   convFactory: Converter.Factory,
                   factory:CallAdapter.Factory) {

    val apiService: GGApi
    val apiV1Service: GGV1Api

    init {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(convFactory)
                .addCallAdapterFactory(factory)
                .client(client)
                .baseUrl(GOODGAME_API_V2_ENDPOINT)
                .build()

        apiService = retrofit.create(GGApi::class.java)

        val retrofitV1 = Retrofit.Builder()
                .addConverterFactory(convFactory)
                .addCallAdapterFactory(factory)
                .client(client)
                .baseUrl(GOODGAME_API_V1_ENDPOINT)
                .build()

        apiV1Service = retrofitV1.create(GGV1Api::class.java)
    }

    companion object {
        const val GOODGAME_API_V1_ENDPOINT = "https://goodgame.ru"
        const val GOODGAME_API_V2_ENDPOINT = "https://api2.goodgame.ru"
        const val GOODGAME_API_HLS = "http://hls.goodgame.ru/hls/"
        // Headers
        const val HEADER_API_V2_VERSION = "Accept: application/vnd.goodgame.v2+json"

        const val GOODGAME_API_AUTH = "/api/token"
        const val GOODGAME_API_CHAT_AUTH = "/ajax/chatlogin"

        const val GOODGAME_API_V2_CHANNEL_SMILES = "/smiles/{channelId}"
        const val GOODGAME_API_V2_ALL_SMILES = "/smiles"
        const val GOODGAME_API_V2_STREAMS = "/streams"

        const val CHANNEL_STATUS = "api/getggchannelstatus"

        const val CACHE_CONTROL = "Cache-Control"

        fun provideCacheInterceptor(): Interceptor {
            return Interceptor { chain ->
                val response = chain.proceed(chain.request())
                val cacheControl: CacheControl

                // re-write response header to force use of cache
                val path = response.request().url().url().path
                if (path.contains("/smiles")) {
                    cacheControl = CacheControl.Builder()
                            .maxAge(1, TimeUnit.DAYS)
                            .build()
                } else {
                    cacheControl = chain.request().cacheControl()
                }

                response.newBuilder()
                        .header(CACHE_CONTROL, cacheControl.toString())
                        .build()
            }
        }
    }
}

