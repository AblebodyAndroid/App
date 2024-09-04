package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.OnboardingRepository
import javax.inject.Inject

class CheckDuplicatedNicknameUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {

    suspend operator fun invoke(
        nickname: String
    ): Boolean = onboardingRepository.checkNickname(nickname)
}
