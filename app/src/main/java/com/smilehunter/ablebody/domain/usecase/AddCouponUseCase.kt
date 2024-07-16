package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.UserRepository
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddCouponUseCase @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(couponCode: String): CouponStatus = withContext(ioDispatcher) {
        userRepository.addCouponByCouponCode(couponCode)
    }
}