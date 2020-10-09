package com.nlx.ggstreams.models

import com.google.gson.annotations.SerializedName

data class ChatProfile(
        var code: Int = -1,
        @SerializedName("user_id")
        var userId: Int = -1,
        var login: String = "",
        var token: String = "",
        var result: Boolean = false
)
