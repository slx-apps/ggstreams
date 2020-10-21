package com.nlx.ggstreams.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GGStream(
        @SerializedName("stream_id")
        var streamId: Int = -1,
        var key: String = "",
        var title: String = "",
        var viewers: Int = -1,
        var usersInChat: Int = 0,
        @SerializedName("player_src")
        var playerSrc: String = "",
        var thumb: String = ""
) : Parcelable