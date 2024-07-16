package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.UserRepository
import com.smilehunter.ablebody.domain.model.UserInfoData
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import com.smilehunter.ablebody.network.model.response.UserDataResponseData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): UserInfoData = withContext(ioDispatcher) {
        userRepository.getMyUserData().data!!.toDomain()
    }


    suspend operator fun invoke(uid: String): UserInfoData = withContext(ioDispatcher) {
        userRepository.getUserData(uid).data!!.toDomain()
    }
}

private fun UserDataResponseData.toDomain(): UserInfoData {
    val isCreator = authorities.any { it.authorityName == "ROLE_CRT" }

    return UserInfoData(
        createDate = createDate,
        modifiedDate = modifiedDate,
        gender = gender,
        uid = uid,
        phoneNumber = phone,
        nickname = nickname,
        name = name,
        height = height,
        weight = weight,
        job = job,
        profileUrl = profileUrl,
        introduction = introduction,
        creatorPoint = creatorPoint,
        userType = if (isCreator) UserInfoData.UserType.CREATOR else UserInfoData.UserType.USER
    )
}