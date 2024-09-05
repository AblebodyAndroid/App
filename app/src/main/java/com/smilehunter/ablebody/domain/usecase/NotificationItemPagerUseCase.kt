package com.smilehunter.ablebody.domain.usecase

import androidx.paging.PagingData
import com.smilehunter.ablebody.domain.model.NotificationItemData
import com.smilehunter.ablebody.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationItemPagerUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {

    operator fun invoke(): Flow<PagingData<NotificationItemData.Content>> {
        return notificationRepository.getMyNoti()
    }
}