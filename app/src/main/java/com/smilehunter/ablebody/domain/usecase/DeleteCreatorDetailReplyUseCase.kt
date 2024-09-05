package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.CommentRepository
import javax.inject.Inject

class DeleteCreatorDetailReplyUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) {

    suspend operator fun invoke(id: Long) {
        commentRepository.creatorDetailDeleteReply(id)
    }
}