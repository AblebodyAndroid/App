package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.OrderManagementRepository
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConfirmPaymentUseCase @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val orderManagementRepository: OrderManagementRepository
) {

    suspend operator fun invoke(
        paymentKey: String,
        orderListId: String,
        amount: String
    ) {
        withContext(ioDispatcher) {
            orderManagementRepository.confirmPayment(
                paymentKey = paymentKey, orderListId = orderListId, amount = amount
            )
        }
    }

}