package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.network.model.response.CreatorDetailLikeUsersResponseData

interface LikeListRepository {

    suspend fun creatorDetailLikeUsersBoard(
        id: Long
    ): List<CreatorDetailLikeUsersResponseData>

    suspend fun creatorDetailLikeUsersComment(
        id: Long
    ): List<CreatorDetailLikeUsersResponseData>

    suspend fun creatorDetailLikeUsersReply(
        id: Long
    ): List<CreatorDetailLikeUsersResponseData>
}