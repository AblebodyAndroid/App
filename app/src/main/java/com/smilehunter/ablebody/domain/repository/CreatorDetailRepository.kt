package com.smilehunter.ablebody.domain.repository

import com.smilehunter.ablebody.domain.model.CommentListData
import com.smilehunter.ablebody.domain.model.CreatorDetailData

interface CreatorDetailRepository {

    suspend fun getCreatorDetailData(id: Long, uid: String): CreatorDetailData

    suspend fun getCreatorDetailComment(id: Long, uid: String): List<CommentListData>

    suspend fun deleteCreatorDetailPage(id: Long)

    suspend fun toggleLike(id: Long)
}