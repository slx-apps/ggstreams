package com.nlx.ggstreams.models

import com.google.gson.annotations.SerializedName


data class EmoteIconsResponse(@SerializedName("_embedded")
                              var smileyContainer: EmoteIconsContainer? = null,
                              @SerializedName("page_count")
                              var pageCount: Int = 0,
                              @SerializedName("page_size")
                              var pageSize: Int = 0,
                              @SerializedName("total_items")
                              var totalItems: Int = 0,
                              var page: Int = 0)

data class EmoteIconsContainer(
        @SerializedName("smiles")
        var emoteIcons: List<EmoteIcon>? = null
)