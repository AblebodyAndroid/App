package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.model.OrderItemId
import com.smilehunter.ablebody.domain.model.OrderRequestItem
import com.smilehunter.ablebody.domain.repository.OrderManagementRepository
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderItemUseCase @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val orderManagementRepository: OrderManagementRepository
) {

    suspend operator fun invoke(
        addressId: Int,
        orderName: String,
        paymentType: String,
        paymentMethod: String,
        easyPayType: String?,
        pointDiscount: Int?,
        deliveryPrice: Int?,
        orderRequestItem: List<OrderRequestItem>
    ): OrderItemId = withContext(ioDispatcher) {
        orderManagementRepository.orderItem(
            addressId = addressId,
            orderName = orderName,
            paymentType = paymentType,
            paymentMethod = paymentMethod,
            easyPayType = easyPayType,
            pointDiscount = pointDiscount,
            deliveryPrice = deliveryPrice,
            orderRequestItem = orderRequestItem
        )
    }
}