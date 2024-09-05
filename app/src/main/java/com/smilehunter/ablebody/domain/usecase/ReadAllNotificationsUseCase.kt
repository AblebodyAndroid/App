package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.NotificationRepository
import javax.inject.Inject

class ReadAllNotificationsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {

    /**
     *  알림 전체 읽음
     */
    suspend operator fun invoke(): String =
        notificationRepository.checkAllMyNoti()
}