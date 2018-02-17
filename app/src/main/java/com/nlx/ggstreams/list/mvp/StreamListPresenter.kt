package com.nlx.ggstreams.list.mvp

import com.nlx.ggstreams.utils.rx.RxUtils
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class StreamListPresenter @Inject constructor(private val view: StreamListMVP.View,
                          private val model: StreamListMVP.Model,
                          private val rxUtils: RxUtils) : StreamListMVP.Presenter {

    override var compositeDisposal = CompositeDisposable()

    private var page: Int = 1

    override fun loadStreams(refresh: Boolean) {

        val currentPage: Int = if (refresh) {
            1
        } else {
            page + 1
        }

        compositeDisposal.add(
                model.fetchStreams(currentPage)
                        .subscribeOn(rxUtils.subscribeScheduler)
                        .observeOn(rxUtils.observeScheduler)
                        .subscribe({
                                    view.streamListLoaded(it.streams, refresh)
                                    page = currentPage
                                }, {
                                    view.handleErrors(it)
                                })
        )
    }

}