package com.nlx.ggstreams.list.data

import com.nlx.ggstreams.models.StreamListResponse
import io.reactivex.Observable
import io.reactivex.Single

interface StreamRepository {
    fun fetchStreams(page: Int): Single<StreamListResponse>
}