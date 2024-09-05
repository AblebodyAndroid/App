package com.smilehunter.ablebody.domain.repository

interface FCMSyncRepository {

    suspend fun updateFCMTokenAndAppVersion()
}