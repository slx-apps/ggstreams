package com.nlx.ggstreams.di.modules

import android.app.Application
import com.nlx.ggstreams.BuildConfig
import com.nlx.ggstreams.rest.GGRestClient
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import com.squareup.picasso.OkHttp3Downloader
import com.nlx.ggstreams.di.UserAgentInterceptor
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {

    private val DISK_CACHE_SIZE = (50 * 1024 * 1024).toLong()//50mb
    private val PICASSO_MEMORY_CACHE_SIZE = 150 * 1024 * 1024//150mb
    private val USER_AGENT = "CustomAndroidClient v/" + BuildConfig.VERSION_NAME

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Application, userAgentInterceptor: UserAgentInterceptor): OkHttpClient {


        // Install an HTTP cache in the application cache directory.
        val cacheDir = File(context.cacheDir, "http")
        val cache = Cache(cacheDir, DISK_CACHE_SIZE)

        val builder = OkHttpClient.Builder()
                .addInterceptor(userAgentInterceptor)
                .addNetworkInterceptor(GGRestClient.provideCacheInterceptor())
                .cache(cache)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun providePicassoClient(context: Application, downloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(context)
                .downloader(downloader)
                .memoryCache(LruCache(PICASSO_MEMORY_CACHE_SIZE))
                .build()
    }

    @Provides
    @Singleton
    fun provideDownloader(okHttpClient: OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(okHttpClient)
    }

	@Provides
    @Singleton
	fun provideUserAgentInterceptor() : UserAgentInterceptor {
		return UserAgentInterceptor(USER_AGENT)
	}
}
