package com.nlx.ggstreams.models

import com.google.gson.annotations.SerializedName

abstract class Response(type: String)

data class HelloResponse(
        val protocolVersion: String = "",
        val serverIdent: String = "",
        val type: String
) : Response(type)

data class PostMessage(
        @SerializedName("channel_id")
        val channelId: Int = -1,
        val text: String?,
        val type: String = ""
) : Response(type)

data class SuccessJoinResponse(
        @SerializedName("channel_id")
        var channelId: Int = 0,
        @SerializedName("channel_name")
        var channelName: String = "",
        var motd: String = "",
        val type: String = ""
) : Response(type)