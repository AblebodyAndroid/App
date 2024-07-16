package com.smilehunter.ablebody.domain.repository

import com.smilehunter.ablebody.network.model.CheckMyNotiResponse
import com.smilehunter.ablebody.network.model.GetMyNotiResponse

interface NotificationRepository {

    suspend fun getMyNoti(page: Int = 0, size: Int = 30): GetMyNotiResponse
    suspend fun checkMyNoti(id: Long): CheckMyNotiResponse
    suspend fun checkAllMyNoti(): CheckMyNotiResponse
}