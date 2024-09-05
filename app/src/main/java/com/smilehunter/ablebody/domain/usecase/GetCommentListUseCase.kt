package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.model.CommentListData
import com.smilehunter.ablebody.domain.repository.CreatorDetailRepository
import javax.inject.Inject

class GetCommentListUseCase @Inject constructor(
    private val creatorDetailRepository: CreatorDetailRepository
) {

    suspend operator fun invoke(id: Long, uid: String): List<CommentListData> =
        creatorDetailRepository.getCreatorDetailComment(id, uid)
}