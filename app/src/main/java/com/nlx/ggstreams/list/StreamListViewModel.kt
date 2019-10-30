package com.nlx.ggstreams.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.*
import android.util.Log
import com.nlx.ggstreams.list.data.StreamListRepository
import com.nlx.ggstreams.models.GGStream
import com.nlx.ggstreams.models.StreamListResponse
import com.nlx.ggstreams.utils.rx.RxUtils
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import javax.inject.Inject

class StreamListViewModel @Inject constructor(
        private val repo: StreamListRepository,
        private val rxUtils: RxUtils
        ) : ViewModel() {

    companion object {
        const val TAG = "StreamListViewModel"
        const val PAGING_LIMIT = 25

    }

    val factory = StreamsDataSourceFactory()
    private var listLiveData: LiveData<PagedList<GGStream>> = LivePagedListBuilder(
            factory,
            PAGING_LIMIT
    ).build()

    fun listLiveData(): LiveData<PagedList<GGStream>> = listLiveData

    fun invalidateList() {
        factory.dataSource?.invalidate()
    }

    fun fetchStreams() {
        repo.fetchStreams(1)
    }

//    private val streamList: Flowable<PagedList<GGStream>> = RxPagedListBuilder(
//            StreamsDataSourceFactory(),
//            25
//    ).buildFlowable(BackpressureStrategy.LATEST)

    private fun getStreamList(page: Int, onFetchedListener: (StreamListResponse) -> Unit) {
        repo.fetchStreams(page)
                .observeOn(rxUtils.observeScheduler)
                .subscribe({
                    onFetchedListener(it)
                }, {
                    Log.d(TAG, "getStreamList: $it")
                })

    }

//    fun streamListObservable(): Flowable<PagedList<GGStream>> = streamList

    inner class StreamsDataSourceFactory : DataSource.Factory<String, GGStream>() {
        var dataSource: StreamsPageKeyedDataSource? = null

        override fun create(): DataSource<String, GGStream> {
            dataSource = StreamsPageKeyedDataSource()
            return dataSource!!
        }

    }

    inner class StreamsPageKeyedDataSource : PageKeyedDataSource<String, GGStream>() {

        override fun loadBefore(
                params: LoadParams<String>,
                callback: LoadCallback<String, GGStream>) {
            // ignored, since we only ever append to our initial load
        }

        override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, GGStream>) {
            Log.d(TAG, "loadAfter: " + params.key.toInt())
            getStreamList(params.key.toInt()) {
                if (it.page == it.pageSize) {
                    callback.onResult(it.streams, null)
                } else {
                    callback.onResult(it.streams, (it.page + 1).toString())
                }
            }
        }

        override fun loadInitial(params: LoadInitialParams<String>,
                callback: LoadInitialCallback<String, GGStream>) {

            Log.d(TAG, "loadInitial: page " + 1)
            getStreamList(1) {
                if (it.page == it.pageSize) {
                    callback.onResult(it.streams, null, null)
                } else {

                    callback.onResult(it.streams, null, (it.page + 1).toString())
                }
            }
        }
    }
}