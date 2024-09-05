package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.CommentRepository
import javax.inject.Inject

class LikeCreatorDetailReplyUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) {

    suspend operator fun invoke(id: Long) {
        commentRepository.creatorDetailLikeReply(id)
    }
}