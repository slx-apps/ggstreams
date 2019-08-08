package com.nlx.ggstreams.stream.mvp

import com.nlx.ggstreams.data.PreferencesUtils
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class StreamPresenter @Inject constructor(private val preferences: PreferencesUtils
                                         ) : StreamMVP.Presenter {

    override var compositeDisposal = CompositeDisposable()

    override fun shouldAutoPlay(): Boolean  = preferences.shouldAutoPlay

    override fun isScrollToLast(): Boolean = preferences.isScrollToLast

}