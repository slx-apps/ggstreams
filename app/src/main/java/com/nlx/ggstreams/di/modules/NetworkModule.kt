package com.nlx.ggstreams.di.modules

import android.content.Context
import android.util.Log
import com.nlx.ggstreams.BuildConfig
import com.nlx.ggstreams.di.PerApp
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
import okhttp3.logging.HttpLoggingInterceptor


@Module
class NetworkModule {

    private val DISK_CACHE_SIZE = (50 * 1024 * 1024).toLong()//50mb
    private val PICASSO_MEMORY_CACHE_SIZE = 150 * 1024 * 1024//150mb
    private val USER_AGENT = "CustomAndroidClient v/" + BuildConfig.VERSION_NAME

    @Provides
    @PerApp
    fun provideOkHttpClient(context: Context, userAgentInterceptor: UserAgentInterceptor): OkHttpClient {


        // Install an HTTP cache in the application cache directory.
        val cacheDir = File(context.cacheDir, "http")
        val cache = Cache(cacheDir, DISK_CACHE_SIZE)

        val builder = OkHttpClient.Builder()
                .addInterceptor(userAgentInterceptor)
                //.addNetworkInterceptor(new StethoInterceptor())
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
    @PerApp
    fun providePicassoClient(context: Context, downloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(context)
                .downloader(downloader)
                //			.indicatorsEnabled(true)
                .memoryCache(LruCache(PICASSO_MEMORY_CACHE_SIZE))
                .build()
    }

    @Provides
    @PerApp
    fun provideDownloader(okHttpClient: OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(okHttpClient)
    }

	@Provides
	@PerApp
	fun provideUserAgentInterceptor() : UserAgentInterceptor {
		return UserAgentInterceptor(USER_AGENT)
	}
}
