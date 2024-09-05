package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.ManageRepository
import javax.inject.Inject

class ReportUserUseCase @Inject constructor(
    private val manageRepository: ManageRepository
) {

    suspend operator fun invoke(
        id: Long,
        reason: String,
        content: String
    ): String = manageRepository.reportUser(id = id, reason = reason, content = content)
}