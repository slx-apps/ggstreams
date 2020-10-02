package com.nlx.ggstreams.stream.di

import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.net.CookieManager

@InstallIn(ApplicationComponent::class)
@Module
object PlayerModule {

    @Provides
//    @PerScreen
    fun provideBandwidthMeter(): DefaultBandwidthMeter = DefaultBandwidthMeter()

    @Provides
//    @PerScreen
    fun provideCookieManager(): CookieManager = CookieManager()

}