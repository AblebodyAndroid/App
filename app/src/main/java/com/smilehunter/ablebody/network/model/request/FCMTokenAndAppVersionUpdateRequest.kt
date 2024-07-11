package com.smilehunter.ablebody.network.model.request

import com.google.gson.annotations.SerializedName

data class FCMTokenAndAppVersionUpdateRequest(
    val fcmToken: String,
    @SerializedName("version") val appVersion: String
)
