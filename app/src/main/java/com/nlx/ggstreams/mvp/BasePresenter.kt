package com.nlx.ggstreams.mvp

import io.reactivex.disposables.CompositeDisposable

interface BasePresenter {

    var compositeDisposal: CompositeDisposable

    fun dispose() {
        compositeDisposal.dispose()
    }

}