package com.smilehunter.ablebody.domain.repository

import com.smilehunter.ablebody.domain.model.DeliveryTrackingData
import com.smilehunter.ablebody.domain.model.OrderItemData
import com.smilehunter.ablebody.domain.model.OrderItemId
import com.smilehunter.ablebody.domain.model.OrderRequestItem
import com.smilehunter.ablebody.domain.model.ReceiptData

interface OrderManagementRepository {

    suspend fun getOrderItems(): List<OrderItemData>

    suspend fun cancelOrderItem(id: String)

    suspend fun getDeliveryTrackingNumber(id: String): DeliveryTrackingData

    suspend fun orderItem(
        addressId: Int,
        orderName: String,
        paymentType: String,
        paymentMethod: String,
        easyPayType: String?,
        pointDiscount: Int?,
        deliveryPrice: Int?,
        orderRequestItem: List<OrderRequestItem>
    ): OrderItemId

    suspend fun getOrderDetailItem(
        id: String
    ): ReceiptData

    /**
     * 주문 아이디 값 반환
     */

    suspend fun confirmPayment(
        paymentKey: String,
        orderListId: String,
        amount: String
    ): String

    /**
     * 주문 아이디 값 반환
     */

    suspend fun handlePaymentFailure(
        code: String,
        message: String,
        orderListId: String
    ): String
}