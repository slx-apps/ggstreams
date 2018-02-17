package com.nlx.ggstreams.models

import com.google.gson.annotations.SerializedName


data class StreamListResponse(@SerializedName("page_count")
                              var pageCount: Int = -1,
                              @SerializedName("page_size")
                              var pageSize: Int = -1,
                              @SerializedName("total_items")
                              var totalItems: Int = -1,
                              var page: Int = -1,
                              var streams: List<GGStream> = listOf())