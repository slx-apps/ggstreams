package com.nlx.ggstreams.models

import com.google.gson.annotations.SerializedName

data class AuthRequest(
        @SerializedName("user_id")
        var userId: Int = -1,
        var token: String = ""
)