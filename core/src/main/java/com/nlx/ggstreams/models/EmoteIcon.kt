package com.nlx.ggstreams.models

import com.google.gson.annotations.SerializedName


data class EmoteIcon(var key: String = "",
                     @SerializedName("donate_lvl")
                     var donateLvl: Int = -1,
                     @SerializedName("is_premium")
                     var isPremium: Boolean = false,
                     @SerializedName("is_paid")
                     var isPaid: Boolean = false,
                     @SerializedName("channel_id")
                     var channelId: String = "",
                     var urls: EmoteIconUrls = EmoteIconUrls())

data class EmoteIconUrls(var img: String = "",
                         var big: String = "",
                         var gif: String = "")