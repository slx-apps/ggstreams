package com.nlx.ggstreams.stream.di

import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.nlx.ggstreams.di.PerApp
import com.nlx.ggstreams.di.PerScreen
import dagger.Module
import dagger.Provides
import java.net.CookieManager

@Module
class PlayerModule {

    @Provides
    @PerApp
    fun provideBandwidthMeter(): DefaultBandwidthMeter = DefaultBandwidthMeter()

    @Provides
    @PerApp
    fun provideCookieManager(): CookieManager = CookieManager()

}