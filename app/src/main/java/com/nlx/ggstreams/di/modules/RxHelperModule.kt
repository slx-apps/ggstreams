package com.nlx.ggstreams.di.modules

import com.nlx.ggstreams.utils.rx.BaseRxUtils
import com.nlx.ggstreams.utils.rx.RxUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RxHelperModule {

    @Provides
    @Singleton
    fun provideRxUtils(): RxUtils = BaseRxUtils(
            AndroidSchedulers.mainThread(),
            Schedulers.io())

}