package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.NotificationRepository
import javax.inject.Inject

class ReadNotificationsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {

    /**
     *  알림 하나의 아이템 읽음
     */

    suspend operator fun invoke(id: Long) =
        notificationRepository.checkMyNoti(id)
}