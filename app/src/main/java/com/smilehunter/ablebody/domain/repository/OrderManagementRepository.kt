package com.smilehunter.ablebody.domain.repository

import com.smilehunter.ablebody.network.model.AddOrderListResponse
import com.smilehunter.ablebody.network.model.GetDeliveryInfoResponse
import com.smilehunter.ablebody.network.model.GetOrderListDetailResponse
import com.smilehunter.ablebody.network.model.GetOrderListResponse
import com.smilehunter.ablebody.network.model.TossPaymentFailResponse
import com.smilehunter.ablebody.network.model.TossPaymentSuccessResponse
import com.smilehunter.ablebody.network.model.request.AddOrderListRequest

interface OrderManagementRepository {

    suspend fun getOrderItems(): GetOrderListResponse

    suspend fun cancelOrderItem(id: String)

    suspend fun getDeliveryTrackingNumber(id: String): GetDeliveryInfoResponse

    suspend fun orderItem(
        addOrderListRequest: AddOrderListRequest
    ): AddOrderListResponse

    suspend fun getOrderDetailItem(
        id: String
    ): GetOrderListDetailResponse

    suspend fun confirmPayment(
        paymentKey: String,
        orderListId: String,
        amount: String
    ): TossPaymentSuccessResponse

    suspend fun handlePaymentFailure(
        code: String,
        message: String,
        orderListId: String
    ): TossPaymentFailResponse
}