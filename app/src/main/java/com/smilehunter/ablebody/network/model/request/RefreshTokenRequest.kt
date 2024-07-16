package com.smilehunter.ablebody.network.model.request

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("refresh_token") val refreshToken: String
)
