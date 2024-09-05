package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.model.UserInfoData
import com.smilehunter.ablebody.domain.repository.UserRepository
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): UserInfoData = withContext(ioDispatcher) {
        userRepository.getMyUserData()
    }


    suspend operator fun invoke(uid: String): UserInfoData = withContext(ioDispatcher) {
        userRepository.getUserData(uid)
    }
}