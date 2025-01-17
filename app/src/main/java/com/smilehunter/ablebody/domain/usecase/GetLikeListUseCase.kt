package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.model.LikeListData
import com.smilehunter.ablebody.domain.model.LikedLocations
import com.smilehunter.ablebody.domain.repository.LikeListRepository
import javax.inject.Inject

class GetLikeListUseCase @Inject constructor(
    private val likeListRepository: LikeListRepository
) {

    suspend operator fun invoke(
        likedLocations: LikedLocations,
        id: Long
    ): List<LikeListData> =
        when (likedLocations) {
            LikedLocations.BOARD -> likeListRepository.creatorDetailLikeUsersBoard(id)
            LikedLocations.COMMENT -> likeListRepository.creatorDetailLikeUsersComment(id)
            LikedLocations.REPLAY -> likeListRepository.creatorDetailLikeUsersReply(id)
        }
}