package com.nlx.ggstreams.ui.list

import androidx.paging.PagingSource
import com.nlx.ggstreams.models.GGStream
import com.nlx.ggstreams.ui.list.data.StreamRepository
import javax.inject.Inject

class StreamsPageKeyedDataSource @Inject constructor(
        private val streamRepository: StreamRepository
): PagingSource<String, GGStream>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, GGStream> {
        return try {
            val nextPageNumber = params.key?.toIntOrNull() ?: 1
            val response = streamRepository.fetchStreamsSingle(nextPageNumber)
            val streamListResponse = response.body()
            LoadResult.Page(
                    data = streamListResponse?.streams ?: listOf(),
                    prevKey = null,
                    nextKey = ((streamListResponse?.page ?: 1) + 1).toString()
            )
        } catch (e: Exception) {
            LoadResult.Error(e.cause ?: Throwable())
        }
    }

}