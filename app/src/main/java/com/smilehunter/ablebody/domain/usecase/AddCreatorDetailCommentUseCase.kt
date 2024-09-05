package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.CommentRepository
import javax.inject.Inject

class AddCreatorDetailCommentUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) {

    suspend operator fun invoke(id: Long, content: String) {
        commentRepository.creatorDetailComment(id, content)
    }
}