package com.smilehunter.ablebody.domain.repository

import com.smilehunter.ablebody.domain.model.LikeListData

interface LikeListRepository {

    suspend fun creatorDetailLikeUsersBoard(
        id: Long
    ): List<LikeListData>

    suspend fun creatorDetailLikeUsersComment(
        id: Long
    ): List<LikeListData>

    suspend fun creatorDetailLikeUsersReply(
        id: Long
    ): List<LikeListData>
}