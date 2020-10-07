package com.nlx.ggstreams.ui.list

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.nlx.ggstreams.ui.list.data.StreamRepository
import com.nlx.ggstreams.models.GGStream
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class StreamsPageKeyedDataSource @Inject constructor(
        private val streamRepository: StreamRepository
): PageKeyedDataSource<String, GGStream>() {

    override fun loadBefore(
            params: LoadParams<String>,
            callback: LoadCallback<String, GGStream>) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, GGStream>) {
        Log.d(StreamListViewModel.TAG, "loadAfter: " + params.key.toInt())

        val response = streamRepository.fetchStreamsSingle(params.key.toInt()).execute()

        if (response.isSuccessful) {
            val res = response.body()
            if (res != null) {
                if (res.page == res.pageSize) {
                    callback.onResult(res.streams, null)
                } else {
                    callback.onResult(res.streams, (res.page + 1).toString())
                }
            }
        }

    }

    override fun loadInitial(params: LoadInitialParams<String>,
                             callback: LoadInitialCallback<String, GGStream>) {
        val response = streamRepository.fetchStreamsSingle(1).execute()

        if (response.isSuccessful) {
            val res = response.body()
            if (res != null) {
                if (res.page == res.pageSize) {
                    callback.onResult(res.streams, null, null)
                } else {
                    callback.onResult(res.streams, null, (res.page + 1).toString())
                }
            }
        }
    }

}