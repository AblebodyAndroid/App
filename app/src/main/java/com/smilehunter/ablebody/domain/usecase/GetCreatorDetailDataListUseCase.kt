package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.model.CreatorDetailData
import com.smilehunter.ablebody.domain.repository.CreatorDetailRepository
import javax.inject.Inject

class GetCreatorDetailDataListUseCase @Inject constructor(
    private val creatorDetailRepository: CreatorDetailRepository
) {

    suspend operator fun invoke(id: Long, userID: String): CreatorDetailData {
        return creatorDetailRepository.getCreatorDetailData(id, userID)
    }
}