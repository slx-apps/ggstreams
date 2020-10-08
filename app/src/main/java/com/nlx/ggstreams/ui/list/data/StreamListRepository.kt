package com.nlx.ggstreams.ui.list.data

import com.nlx.ggstreams.models.StreamListResponse
import com.nlx.ggstreams.rest.GGApi
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class StreamListRepository @Inject constructor(val api: GGApi) : StreamRepository {

    override suspend fun fetchStreamsSingle(page: Int): Response<StreamListResponse> {
        return api.getStreamList(page)
    }

}