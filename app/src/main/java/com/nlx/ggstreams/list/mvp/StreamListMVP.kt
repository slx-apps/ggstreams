package com.nlx.ggstreams.list.mvp

import com.nlx.ggstreams.models.GGStream
import com.nlx.ggstreams.models.StreamListResponse
import com.nlx.ggstreams.mvp.BasePresenter
import com.nlx.ggstreams.mvp.BaseView
import io.reactivex.Single

interface StreamListMVP {

    interface Model {
        fun fetchStreams(page: Int) : Single<StreamListResponse>
    }

    interface Presenter : BasePresenter {
        fun loadStreams(refresh: Boolean)
    }

    interface View : BaseView {
        fun streamListLoaded(list: List<GGStream>, refresh: Boolean)
    }

}
