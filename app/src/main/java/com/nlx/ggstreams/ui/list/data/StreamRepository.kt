package com.nlx.ggstreams.ui.list.data

import com.nlx.ggstreams.models.StreamListResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call

interface StreamRepository {
    fun fetchStreamsSingle(page: Int): Call<StreamListResponse>
}