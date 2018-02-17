package com.nlx.ggstreams.models

import com.google.gson.annotations.SerializedName

data class CountersData(
        @SerializedName("user_id")
        var userId: Int = -1,
        @SerializedName("clients_in_channel")
        var clientsInChannel: Int = 0,
        @SerializedName("users_in_channel")
        var usersInChannel: Int = 0)