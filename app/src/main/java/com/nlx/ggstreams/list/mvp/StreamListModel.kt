package com.nlx.ggstreams.list.mvp

import com.nlx.ggstreams.models.StreamListResponse
import com.nlx.ggstreams.rest.GGApi
import io.reactivex.Observable
import javax.inject.Inject

class StreamListModel @Inject constructor(val api: GGApi) : StreamListMVP.Model {

    override fun fetchStreams(page: Int): Observable<StreamListResponse> {
        return api.streams(page)
    }
}
