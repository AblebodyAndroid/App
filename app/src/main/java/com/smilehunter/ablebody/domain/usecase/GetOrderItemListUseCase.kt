package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.data.repository.OrderManagementRepository
import com.smilehunter.ablebody.model.OrderItemData
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import com.smilehunter.ablebody.network.model.response.GetOrderListResponseData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetOrderItemListUseCase @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val orderManagementRepository: OrderManagementRepository
) {

    suspend operator fun invoke(): List<OrderItemData> = withContext(ioDispatcher) {
        orderManagementRepository.getOrderItems().data!!.map { it.toDomain() }
    }
}

private fun GetOrderListResponseData.toDomain(): OrderItemData {
    val itemOptionDetailList = mutableListOf<OrderItemData.ItemOptionDetail>()
    orderListItemList.forEach { orders ->
        orders.colorOption?.let { colorOption ->
            itemOptionDetailList.add(
                OrderItemData.ItemOptionDetail(
                    id = 0,
                    orderNumber = id,
                    itemOption = "색상",
                    itemOptionDetail = colorOption
                )
            )
        }
        orders.sizeOption?.let { sizeOption ->
            itemOptionDetailList.add(
                OrderItemData.ItemOptionDetail(
                    id = 0,
                    orderNumber = id,
                    itemOption = "사이즈",
                    itemOptionDetail = sizeOption
                )
            )
        }
    }

    return OrderItemData(
        id = id,
        itemName = orderListItemList.first().itemName,
        itemImageURL = orderListItemList.first().itemImage,
        amountOfPayment = amount,
        brandName = orderListItemList.first().brand,
        itemOptionDetailList = itemOptionDetailList,
        orderStatus = when (orderStatus) {
            GetOrderListResponseData.OrderStatus.WAIT_FOR_PAYMENT -> OrderItemData.OrderStatus.DEPOSIT_WAITING
            GetOrderListResponseData.OrderStatus.CONFIRMATION_OF_DEPOSIT -> OrderItemData.OrderStatus.DEPOSIT_COMPLETED
            GetOrderListResponseData.OrderStatus.DELIVERY_STARTS -> OrderItemData.OrderStatus.ON_DELIVERY
            GetOrderListResponseData.OrderStatus.DELIVERY_COMPLETED -> OrderItemData.OrderStatus.DELIVERY_COMPLETED
            GetOrderListResponseData.OrderStatus.ORDER_CANCELED -> OrderItemData.OrderStatus.ORDER_CANCELED
            GetOrderListResponseData.OrderStatus.REQUEST_FOR_REFUND_RECOVERY -> OrderItemData.OrderStatus.REFUND_REQUEST
            GetOrderListResponseData.OrderStatus.REFUND_COMPLETED -> OrderItemData.OrderStatus.REFUND_COMPLETED
            GetOrderListResponseData.OrderStatus.EXCHANGE_ORDER_ACCEPTED -> OrderItemData.OrderStatus.EXCHANGE_REQUEST
            GetOrderListResponseData.OrderStatus.EXCHANGE_PROCESSING -> OrderItemData.OrderStatus.ON_EXCHANGE_DELIVERY
            GetOrderListResponseData.OrderStatus.EXCHANGE_COMPLETED -> OrderItemData.OrderStatus.EXCHANGE_COMPLETED
            GetOrderListResponseData.OrderStatus.SHIPMENT_PROCESSING -> OrderItemData.OrderStatus.DEPOSIT_COMPLETED
            GetOrderListResponseData.OrderStatus.SHIPMENT_COMPLETED -> OrderItemData.OrderStatus.DEPOSIT_COMPLETED
            GetOrderListResponseData.OrderStatus.CONFIRMATION_OF_PURCHASE -> OrderItemData.OrderStatus.DELIVERY_COMPLETED
            GetOrderListResponseData.OrderStatus.PAYMENT_ERROR -> OrderItemData.OrderStatus.DEPOSIT_WAITING
            GetOrderListResponseData.OrderStatus.REQUEST_FOR_EXCHANGE_RECOVERY -> OrderItemData.OrderStatus.ON_EXCHANGE_DELIVERY
            GetOrderListResponseData.OrderStatus.EXCHANGE_RECOVERY_COMPLETED -> OrderItemData.OrderStatus.ON_EXCHANGE_DELIVERY
            GetOrderListResponseData.OrderStatus.EXCHANGE_DELIVERY_COMPLETED -> OrderItemData.OrderStatus.ON_EXCHANGE_DELIVERY
            GetOrderListResponseData.OrderStatus.EXCHANGE_CANCELED -> OrderItemData.OrderStatus.ON_EXCHANGE_DELIVERY
            GetOrderListResponseData.OrderStatus.REFUND_RECOVERY_COMPLETED -> OrderItemData.OrderStatus.REFUND_REQUEST
            GetOrderListResponseData.OrderStatus.REFUND_DELIVERY_COMPLETED -> OrderItemData.OrderStatus.REFUND_REQUEST
            GetOrderListResponseData.OrderStatus.REFUND_PROCESSING -> OrderItemData.OrderStatus.REFUND_REQUEST
        },
        orderedDate = LocalDateTime.parse(orderedDate, DateTimeFormatter.ISO_ZONED_DATE_TIME).let {
            "${it.year}.${it.monthValue}.${it.dayOfMonth}"
        }
    )
}