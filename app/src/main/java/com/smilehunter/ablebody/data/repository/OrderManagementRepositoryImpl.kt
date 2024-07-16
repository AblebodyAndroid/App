package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.domain.repository.OrderManagementRepository
import com.smilehunter.ablebody.network.model.request.AddOrderListRequest
import com.smilehunter.ablebody.network.model.AddOrderListResponse
import com.smilehunter.ablebody.network.model.GetDeliveryInfoResponse
import com.smilehunter.ablebody.network.model.GetOrderListDetailResponse
import com.smilehunter.ablebody.network.model.GetOrderListResponse
import com.smilehunter.ablebody.network.model.TossPaymentFailResponse
import com.smilehunter.ablebody.network.model.TossPaymentSuccessResponse
import com.smilehunter.ablebody.network.NetworkService
import javax.inject.Inject

class OrderManagementRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
): OrderManagementRepository {
    override suspend fun getOrderItems(): GetOrderListResponse {
        return networkService.getOrderList()
    }

    override suspend fun cancelOrderItem(id: String) {
        networkService.cancelOrderList(id)
    }

    override suspend fun getDeliveryTrackingNumber(id: String): GetDeliveryInfoResponse {
        return networkService.getDeliveryInfo(id)
    }

    override suspend fun orderItem(
        addOrderListRequest: AddOrderListRequest
    ): AddOrderListResponse {
        return networkService.addOrderList(addOrderListRequest)
    }

    override suspend fun getOrderDetailItem(id: String): GetOrderListDetailResponse {
        return networkService.getOrderListDetail(id)
    }

    override suspend fun confirmPayment(paymentKey: String, orderListId: String, amount: String): TossPaymentSuccessResponse {
        return networkService.tossPaymentSuccess(
            paymentKey = paymentKey,
            orderListId = orderListId,
            amount = amount
        )
    }

    override suspend fun handlePaymentFailure(
        code: String,
        message: String,
        orderListId: String,
    ): TossPaymentFailResponse {
        return networkService.tossPaymentFail(
            code = code,
            message = message,
            orderListId = orderListId
        )
    }

}