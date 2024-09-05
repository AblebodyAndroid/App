package com.smilehunter.ablebody.domain.repository

import androidx.paging.PagingData
import com.smilehunter.ablebody.domain.model.NotificationItemData
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    fun getMyNoti(): Flow<PagingData<NotificationItemData.Content>>

    suspend fun checkMyNoti(id: Long): String

    suspend fun checkAllMyNoti(): String
}