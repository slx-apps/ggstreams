package com.nlx.ggstreams.di.modules

import com.nlx.ggstreams.di.PerApp
import com.nlx.ggstreams.utils.rx.BaseRxUtils
import com.nlx.ggstreams.utils.rx.RxUtils
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class RxHelperModule {

    @Provides
    @PerApp
    fun provideRxUtils(): RxUtils = BaseRxUtils(
            AndroidSchedulers.mainThread(),
            Schedulers.io())

}