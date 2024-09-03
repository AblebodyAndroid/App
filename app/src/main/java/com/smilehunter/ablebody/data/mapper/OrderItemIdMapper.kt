package com.smilehunter.ablebody.data.mapper

import com.smilehunter.ablebody.domain.model.OrderItemId
import com.smilehunter.ablebody.network.model.response.AddOrderListResponseData

fun AddOrderListResponseData.toDomain(): OrderItemId = OrderItemId(
    orderId = orderId,
    orderName = orderName
)