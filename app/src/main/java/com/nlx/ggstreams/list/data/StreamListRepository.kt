package com.nlx.ggstreams.list.data

import com.nlx.ggstreams.models.StreamListResponse
import com.nlx.ggstreams.rest.GGApi
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class StreamListRepository @Inject constructor(val api: GGApi) : StreamRepository {

    override fun fetchStreams(page: Int): Single<StreamListResponse> {
        return api.streams(page)
    }


}