package com.nlx.ggstreams.list.mvp

import com.nlx.ggstreams.models.StreamListResponse
import com.nlx.ggstreams.rest.GGApi
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class StreamListModel @Inject constructor(val api: GGApi) : StreamListMVP.Model {

    override fun fetchStreams(page: Int): Single<StreamListResponse> {
        return api.streams(page)
    }
}
