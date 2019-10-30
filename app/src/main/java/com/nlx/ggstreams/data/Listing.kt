package com.nlx.ggstreams.data

import androidx.paging.PagedList
import com.nlx.ggstreams.models.GGStream
import io.reactivex.Flowable

data class Listing<T> (
        val pagedList: Flowable<PagedList<T>>,
        val networkState: Flowable<NetworkState>,
        val refreshState: Flowable<NetworkState>,
        val refresh: () -> Unit,
        val retry: () -> Unit)
