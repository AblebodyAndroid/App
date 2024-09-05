package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.data.mapper.toDomain
import com.smilehunter.ablebody.domain.model.LikeListData
import com.smilehunter.ablebody.domain.repository.LikeListRepository
import com.smilehunter.ablebody.network.model.response.CreatorDetailLikeUsersResponseData
import com.smilehunter.ablebody.network.NetworkService
import javax.inject.Inject

class LikeListRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
): LikeListRepository {
    override suspend fun creatorDetailLikeUsersBoard(id: Long): List<LikeListData> =
        networkService.creatorDetailLikeUsersBoard(id).data!!.map { it.toDomain() }

    override suspend fun creatorDetailLikeUsersComment(id: Long): List<LikeListData> =
        networkService.creatorDetailLikeUsersComment(id).data!!.map { it.toDomain() }

    override suspend fun creatorDetailLikeUsersReply(id: Long): List<LikeListData> =
        networkService.creatorDetailLikeUsersReply(id).data!!.map { it.toDomain() }
}