package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.OnboardingRepository
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckNicknameUseCase @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val onboardingRepository: OnboardingRepository
) {

    suspend operator fun invoke(nickname: String): Boolean =
        withContext(ioDispatcher) {
            onboardingRepository.checkNickname(nickname).body()!!.success
        }
}