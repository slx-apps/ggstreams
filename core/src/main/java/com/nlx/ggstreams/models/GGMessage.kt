package com.nlx.ggstreams.models

import com.google.gson.annotations.SerializedName

data class GGMessage (
        @SerializedName("channel_id")
        val channelId: Int = -1,
        @SerializedName("user_id")
        val userId: Int = -1,
        @SerializedName("user_name")
        val userName: String = "",
        val color: String = "",
        @SerializedName("message_id")
        val messageId: Int = -1,
        val timestamp: Long = -1,
        val text: String = "",
        val icon: String = "",
        val hideIcon: Int = -1,
        val type: String
) : Response(type)