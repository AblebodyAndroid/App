package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.model.CouponData
import com.smilehunter.ablebody.domain.repository.UserRepository
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCouponListUseCase @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): List<CouponData> = withContext(ioDispatcher) {
        userRepository.getCouponBags()
    }
}