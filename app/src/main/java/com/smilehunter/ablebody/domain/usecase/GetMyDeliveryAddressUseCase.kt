package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.model.DeliveryAddressData
import com.smilehunter.ablebody.domain.repository.UserRepository
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMyDeliveryAddressUseCase @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): DeliveryAddressData = withContext(ioDispatcher) {
        userRepository.getMyAddress()
    }
}