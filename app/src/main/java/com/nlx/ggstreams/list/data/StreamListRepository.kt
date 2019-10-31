package com.nlx.ggstreams.list.data

import com.nlx.ggstreams.models.StreamListResponse
import com.nlx.ggstreams.rest.GGApi
import io.reactivex.Single
import retrofit2.Call
import javax.inject.Inject

class StreamListRepository @Inject constructor(val api: GGApi) : StreamRepository {

    override fun fetchStreamsSingle(page: Int): Call<StreamListResponse> {
        return api.getStreamList(page)
    }

}