package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.OnboardingRepository
import javax.inject.Inject

class SendAuthenticationNumberUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {

    suspend operator fun invoke(
        phoneNumber: String
    ): Long = onboardingRepository.sendSMS(phoneNumber)
}
