package com.nlx.ggstreams.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.*
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import com.nlx.ggstreams.ui.list.data.StreamListRepository
import com.nlx.ggstreams.models.GGStream
import com.nlx.ggstreams.models.StreamListResponse
import com.nlx.ggstreams.utils.rx.RxUtils
import javax.inject.Inject

class StreamListViewModel @ViewModelInject constructor(
        private val factory: StreamsDataSourceFactory
) : ViewModel() {

    companion object {
        const val TAG = "StreamListViewModel"
        const val PAGING_LIMIT = 25
    }

    private var listLiveData: LiveData<PagedList<GGStream>> = LivePagedListBuilder(
            factory,
            PAGING_LIMIT
    ).build()

    fun listLiveData(): LiveData<PagedList<GGStream>> = listLiveData

    fun invalidateList() {
        factory.dataSource.invalidate()
    }
}