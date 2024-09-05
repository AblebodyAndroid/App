package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.FCMSyncRepository
import javax.inject.Inject

class SyncAppStatusUseCase @Inject constructor(
    private val fcmSyncRepository: FCMSyncRepository
) {

    suspend operator fun invoke() {
        fcmSyncRepository.updateFCMTokenAndAppVersion()
    }
}