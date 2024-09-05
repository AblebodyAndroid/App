package com.smilehunter.ablebody.domain.usecase

import androidx.paging.PagingData
import com.smilehunter.ablebody.domain.model.UserBoardData
import com.smilehunter.ablebody.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserBoardPagerUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<PagingData<UserBoardData.Content>> {
        return userRepository.getMyBoard(null)
    }

    operator fun invoke(uid: String): Flow<PagingData<UserBoardData.Content>> {
        return userRepository.getMyBoard(uid)
    }
}