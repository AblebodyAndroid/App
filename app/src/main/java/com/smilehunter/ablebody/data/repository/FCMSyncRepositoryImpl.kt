package com.smilehunter.ablebody.data.repository

import com.google.firebase.messaging.FirebaseMessaging
import com.smilehunter.ablebody.BuildConfig
import com.smilehunter.ablebody.domain.repository.FCMSyncRepository
import com.smilehunter.ablebody.network.NetworkService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FCMSyncRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : FCMSyncRepository {

    override suspend fun updateFCMTokenAndAppVersion() {
        val fcmToken = FirebaseMessaging.getInstance().token.await()
        val appVersion = BuildConfig.VERSION_NAME
        networkService.updateFCMTokenAndAppVersion(fcmToken = fcmToken, appVersion = appVersion)
    }
}