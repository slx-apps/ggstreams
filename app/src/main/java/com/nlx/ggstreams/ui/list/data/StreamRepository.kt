package com.nlx.ggstreams.ui.list.data

import com.nlx.ggstreams.models.StreamListResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response

interface StreamRepository {
    suspend fun fetchStreamsSingle(page: Int): Response<StreamListResponse>
}