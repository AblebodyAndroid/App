package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.OrderManagementRepository
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HandlePaymentFailureUseCase @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val orderManagementRepository: OrderManagementRepository
) {

    suspend operator fun invoke(
        code: String,
        message: String,
        orderListId: String
    ) {
        withContext(ioDispatcher) {
            orderManagementRepository.handlePaymentFailure(
                code = code,
                message = message,
                orderListId = orderListId
            )
        }
    }
}