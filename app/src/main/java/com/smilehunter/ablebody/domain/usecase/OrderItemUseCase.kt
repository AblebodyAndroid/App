package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.OrderManagementRepository
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import com.smilehunter.ablebody.network.model.request.AddOrderListRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderItemUseCase @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val orderManagementRepository: OrderManagementRepository
) {

    suspend operator fun invoke(
        addOrderListRequest: AddOrderListRequest
    ): String = withContext(ioDispatcher) {
        orderManagementRepository.orderItem(addOrderListRequest).data!!.orderId
    }
}