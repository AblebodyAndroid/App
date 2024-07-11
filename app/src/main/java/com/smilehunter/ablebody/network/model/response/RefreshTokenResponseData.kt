package com.smilehunter.ablebody.network.model.response

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponseData(
    @SerializedName("auth_token") val authToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
)
