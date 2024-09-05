package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.model.Gender
import com.smilehunter.ablebody.domain.repository.OnboardingRepository
import javax.inject.Inject

class RegisterAppUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {

    suspend operator fun invoke(
        gender: Gender,
        nickname: String,
        profileImage: Int,
        verifyingCode: String,
        agreeRequiredConsent: Boolean,
        agreeMarketingConsent: Boolean
    ): Boolean = onboardingRepository.createNewUser(
        gender = gender,
        nickname = nickname,
        profileImage = profileImage,
        verifyingCode = verifyingCode,
        agreeRequiredConsent = agreeRequiredConsent,
        agreeMarketingConsent = agreeMarketingConsent
    )
}
