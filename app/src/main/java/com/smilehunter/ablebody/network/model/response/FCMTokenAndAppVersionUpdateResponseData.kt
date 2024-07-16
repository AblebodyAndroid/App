package com.smilehunter.ablebody.network.model.response

import com.google.gson.annotations.SerializedName

data class FCMTokenAndAppVersionUpdateResponseData(
    val fcmToken: String,
    @SerializedName("version") val appVersion: String
)
