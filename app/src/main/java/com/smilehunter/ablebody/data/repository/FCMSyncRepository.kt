package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.network.model.response.FCMTokenAndAppVersionUpdateResponseData

interface FCMSyncRepository {
    suspend fun updateFCMTokenAndAppVersion(fcmToken: String, appVersion: String): FCMTokenAndAppVersionUpdateResponseData
}