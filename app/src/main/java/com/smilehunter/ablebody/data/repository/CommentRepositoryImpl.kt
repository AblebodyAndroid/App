package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.data.mapper.toDomain
import com.smilehunter.ablebody.domain.model.LikeListData
import com.smilehunter.ablebody.domain.repository.CommentRepository
import com.smilehunter.ablebody.network.NetworkService
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : CommentRepository {
    override suspend fun creatorDetailLikeBoard(id: Long) {
        networkService.creatorDetailLikeBoard(id)
    }

    override suspend fun creatorDetailLikeComment(id: Long) {
        networkService.creatorDetailLikeComment(id)
    }

    override suspend fun creatorDetailLikeReply(id: Long) {
        networkService.creatorDetailLikeReply(id)
    }

    override suspend fun creatorDetailLikeUsersBoard(id: Long): List<LikeListData> =
        networkService.creatorDetailLikeUsersBoard(id).data!!.map { it.toDomain() }


    override suspend fun creatorDetailLikeUsersComment(id: Long): List<LikeListData> =
        networkService.creatorDetailLikeUsersComment(id).data!!.map { it.toDomain() }

    override suspend fun creatorDetailLikeUsersReply(id: Long): List<LikeListData> =
        networkService.creatorDetailLikeUsersReply(id).data!!.map { it.toDomain() }

    override suspend fun creatorDetailComment(
        id: Long,
        content: String
    ) {
        networkService.creatorDetailComment(id, content).data
    }

    override suspend fun creatorDetailReply(
        id: Long,
        content: String
    ) {
        networkService.creatorDetailReply(id, content).data
    }

    override suspend fun creatorDetailDeleteComment(id: Long) {
        networkService.creatorDetailDeleteComment(id)
    }

    override suspend fun creatorDetailDeleteReply(id: Long) {
        networkService.creatorDetailDeleteReply(id)
    }
}