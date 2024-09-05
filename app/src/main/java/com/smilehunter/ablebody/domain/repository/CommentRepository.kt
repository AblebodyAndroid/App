package com.smilehunter.ablebody.domain.repository

import com.smilehunter.ablebody.domain.model.LikeListData

interface CommentRepository {

    suspend fun creatorDetailLikeBoard(id: Long)

    suspend fun creatorDetailLikeComment(id: Long)

    suspend fun creatorDetailLikeReply(id: Long)

    suspend fun creatorDetailLikeUsersBoard(id: Long): List<LikeListData>

    suspend fun creatorDetailLikeUsersComment(id: Long): List<LikeListData>

    suspend fun creatorDetailLikeUsersReply(id: Long): List<LikeListData>

    suspend fun creatorDetailComment(id: Long, content: String)

    suspend fun creatorDetailReply(id: Long, content: String)

    suspend fun creatorDetailDeleteComment(id: Long)

    suspend fun creatorDetailDeleteReply(id: Long)
}