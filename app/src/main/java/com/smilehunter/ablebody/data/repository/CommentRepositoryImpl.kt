package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.domain.repository.CommentRepository
import com.smilehunter.ablebody.network.model.response.CreatorDetailCommentResponseData
import com.smilehunter.ablebody.network.model.response.CreatorDetailLikeUsersResponseData
import com.smilehunter.ablebody.network.model.response.CreatorDetailReplyResponseData
import com.smilehunter.ablebody.network.NetworkService
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
): CommentRepository {
    override suspend fun creatorDetailLikeBoard(id: Long) {
        networkService.creatorDetailLikeBoard(id)
    }

    override suspend fun creatorDetailLikeComment(id: Long) {
        networkService.creatorDetailLikeComment(id)
    }

    override suspend fun creatorDetailLikeReply(id: Long) {
        networkService.creatorDetailLikeReply(id)
    }

    override suspend fun creatorDetailLikeUsersBoard(id: Long): List<CreatorDetailLikeUsersResponseData> =
        networkService.creatorDetailLikeUsersBoard(id).data!!


    override suspend fun creatorDetailLikeUsersComment(id: Long): List<CreatorDetailLikeUsersResponseData> =
        networkService.creatorDetailLikeUsersComment(id).data!!

    override suspend fun creatorDetailLikeUsersReply(id: Long): List<CreatorDetailLikeUsersResponseData> =
        networkService.creatorDetailLikeUsersReply(id).data!!

    override suspend fun creatorDetailComment(
        id: Long,
        content: String
    ): CreatorDetailCommentResponseData =
        networkService.creatorDetailComment(id, content).data!!

    override suspend fun creatorDetailReply(
        id: Long,
        content: String
    ): CreatorDetailReplyResponseData =
        networkService.creatorDetailReply(id, content).data!!

    override suspend fun creatorDetailDeleteComment(id: Long) {
        networkService.creatorDetailDeleteComment(id)
    }

    override suspend fun creatorDetailDeleteReply(id: Long) {
        networkService.creatorDetailDeleteReply(id)
    }
}