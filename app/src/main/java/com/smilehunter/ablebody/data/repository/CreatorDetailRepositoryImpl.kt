package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.data.mapper.toDomain
import com.smilehunter.ablebody.domain.model.CommentListData
import com.smilehunter.ablebody.domain.model.CreatorDetailData
import com.smilehunter.ablebody.domain.repository.CreatorDetailRepository
import com.smilehunter.ablebody.network.NetworkService
import javax.inject.Inject

class CreatorDetailRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : CreatorDetailRepository {

    override suspend fun getCreatorDetailData(id: Long, uid: String): CreatorDetailData {
        return networkService.creatorDetail(id).data!!.toDomain(uid)
    }

    override suspend fun getCreatorDetailComment(id: Long, uid: String): List<CommentListData> {
        return networkService.creatorDetail(id).data!!.commentAndReplies.map { it.toDomain(uid) }
    }

    override suspend fun deleteCreatorDetailPage(id: Long) {
        networkService.creatorDetailDelete(id)
    }

    override suspend fun toggleLike(id: Long) {
        networkService.creatorDetailLikeBoard(id)
    }
}