package com.nlx.ggstreams.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthResponse (
        val success: Boolean = false,
        val scope: Boolean = false,
        @SerializedName("access_token")
        val accessToken: String = "",
        @SerializedName("refresh_token")
        val refreshToken: String = ""
        ) : Parcelable