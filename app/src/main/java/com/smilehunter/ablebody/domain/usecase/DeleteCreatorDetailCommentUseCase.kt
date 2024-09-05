package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.CommentRepository
import javax.inject.Inject

class DeleteCreatorDetailCommentUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) {

    suspend operator fun invoke(id: Long) {
        commentRepository.creatorDetailDeleteComment(id)
    }
}