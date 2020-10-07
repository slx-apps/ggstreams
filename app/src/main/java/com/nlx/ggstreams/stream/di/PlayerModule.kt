package com.nlx.ggstreams.stream.di

import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import java.net.CookieManager

@Module
@InstallIn(ActivityComponent::class)
object PlayerModule {

    @Provides
    fun provideBandwidthMeter(): DefaultBandwidthMeter = DefaultBandwidthMeter()

    @Provides
    fun provideCookieManager(): CookieManager = CookieManager()

}