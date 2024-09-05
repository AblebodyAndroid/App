package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.data.mapper.toDomain
import com.smilehunter.ablebody.data.mapper.toNetworkModel
import com.smilehunter.ablebody.domain.model.DeliveryTrackingData
import com.smilehunter.ablebody.domain.model.OrderItemData
import com.smilehunter.ablebody.domain.model.OrderItemId
import com.smilehunter.ablebody.domain.model.OrderRequestItem
import com.smilehunter.ablebody.domain.model.ReceiptData
import com.smilehunter.ablebody.domain.repository.OrderManagementRepository
import com.smilehunter.ablebody.network.NetworkService
import com.smilehunter.ablebody.network.model.request.AddOrderListRequest
import javax.inject.Inject

class OrderManagementRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : OrderManagementRepository {

    override suspend fun getOrderItems(): List<OrderItemData> {
        return networkService.getOrderList().data!!.map { it.toDomain() }
    }

    override suspend fun cancelOrderItem(id: String) {
        networkService.cancelOrderList(id)
    }

    override suspend fun getDeliveryTrackingNumber(id: String): DeliveryTrackingData {
        return networkService.getDeliveryInfo(id).data!!.toDomain()
    }

    override suspend fun orderItem(
        addressId: Int,
        orderName: String,
        paymentType: String,
        paymentMethod: String,
        easyPayType: String?,
        pointDiscount: Int?,
        deliveryPrice: Int?,
        orderRequestItem: List<OrderRequestItem>
    ): OrderItemId {
        return networkService.addOrderList(
            AddOrderListRequest(
                addressId = addressId,
                orderName = orderName,
                paymentType = paymentType,
                paymentMethod = paymentMethod,
                easyPayType = easyPayType,
                pointDiscount = pointDiscount,
                deliveryPrice = deliveryPrice,
                orderListItemReqDtoList = orderRequestItem.map { it.toNetworkModel() }
            )
        ).data!!.toDomain()
    }

    override suspend fun getOrderDetailItem(id: String): ReceiptData {
        return networkService.getOrderListDetail(id).data!!.toDomain()
    }

    override suspend fun confirmPayment(
        paymentKey: String,
        orderListId: String,
        amount: String
    ): String {
        return networkService.tossPaymentSuccess(
            paymentKey = paymentKey,
            orderListId = orderListId,
            amount = amount
        )
            .data!!
    }

    override suspend fun handlePaymentFailure(
        code: String,
        message: String,
        orderListId: String,
    ): String {
        return networkService.tossPaymentFail(
            code = code,
            message = message,
            orderListId = orderListId
        )
            .data!!
    }

}