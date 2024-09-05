package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.OnboardingRepository
import javax.inject.Inject

class CheckAuthenticationNumberUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {

    suspend operator fun invoke(
        phoneConfirmId: Long,
        verifyingCode: String
    ): Boolean = onboardingRepository.checkSMS(phoneConfirmId, verifyingCode)
}
