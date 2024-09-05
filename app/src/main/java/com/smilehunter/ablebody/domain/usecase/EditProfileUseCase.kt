package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.UserRepository
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher.IO
import com.smilehunter.ablebody.network.di.Dispatcher
import com.smilehunter.ablebody.network.model.request.EditProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.InputStream
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        editProfile: EditProfile,
        profileImageInputStream: InputStream?
    ): EditProfileResult = withContext(ioDispatcher) {
        val result = userRepository.editProfile(
            editProfile = editProfile,
            profileImageInputStream = profileImageInputStream
        )
        profileImageInputStream?.close()


        if (result) {
            return@withContext EditProfileResult.SUCCESS
        }
        EditProfileResult.FAIL
    }

}