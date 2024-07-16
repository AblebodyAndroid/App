package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.network.model.response.CreatorDetailResponseData

interface CreatorDetailRepository {

    suspend fun getCreatorDetailData(id: Long): CreatorDetailResponseData
    suspend fun deleteCreatorDetailPage(id: Long)
    suspend fun toggleLike(id: Long)
}