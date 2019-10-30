package com.nlx.ggstreams.stream.di

import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.nlx.ggstreams.di.PerApp
import com.nlx.ggstreams.di.PerScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import java.net.CookieManager

@Module
object PlayerModule {

    @Provides
    @PerScreen
    fun provideBandwidthMeter(): DefaultBandwidthMeter = DefaultBandwidthMeter()

    @Provides
    @PerScreen
    fun provideCookieManager(): CookieManager = CookieManager()

}