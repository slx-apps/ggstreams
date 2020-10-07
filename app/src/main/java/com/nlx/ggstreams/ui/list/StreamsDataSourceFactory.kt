package com.nlx.ggstreams.ui.list

import androidx.paging.DataSource
import com.nlx.ggstreams.ui.list.data.StreamRepository
import com.nlx.ggstreams.models.GGStream
import javax.inject.Inject

class StreamsDataSourceFactory @Inject constructor(val streamRepository: StreamRepository
) : DataSource.Factory<String, GGStream>() {

    lateinit var dataSource: StreamsPageKeyedDataSource

    override fun create(): DataSource<String, GGStream> {
        dataSource = StreamsPageKeyedDataSource(streamRepository)
        return dataSource
    }

}