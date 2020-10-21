package com.nlx.ggstreams.ui.list

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nlx.ggstreams.models.GGStream
import com.nlx.ggstreams.ui.list.data.StreamRepository
import kotlinx.coroutines.flow.Flow

class StreamListViewModel @ViewModelInject constructor(
        private val streamRepository: StreamRepository
) : ViewModel() {

    companion object {
        const val TAG = "StreamListViewModel"
        const val PAGING_LIMIT = 10
    }

    private lateinit var pager: Pager<String, GGStream>
    private lateinit var ds: StreamsPageKeyedDataSource
    private lateinit var flow: Flow<PagingData<GGStream>>

    init {
        createPagingFlow()
    }

    fun listLiveData(): Flow<PagingData<GGStream>> = flow

    fun invalidateList() {
        ds.invalidate()
    }

    private fun createPagingFlow() {
        pager = Pager(PagingConfig(pageSize = PAGING_LIMIT)) {
            StreamsPageKeyedDataSource(streamRepository).also {
               ds = it
            }
        }
        flow = pager.flow
        .cachedIn(viewModelScope)
    }
}

