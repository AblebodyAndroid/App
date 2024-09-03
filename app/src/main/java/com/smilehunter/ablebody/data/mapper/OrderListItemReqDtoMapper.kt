package com.smilehunter.ablebody.data.mapper

import com.smilehunter.ablebody.domain.model.OrderRequestItem
import com.smilehunter.ablebody.network.model.request.AddOrderListRequest

fun OrderRequestItem.toNetworkModel() = AddOrderListRequest.OrderListItemReqDto(
    itemId = itemId,
    couponBagsId = couponBagsId,
    colorOption = colorOption,
    sizeOption = sizeOption,
    itemPrice = itemPrice,
    itemCount = itemCount,
    itemDiscount = itemDiscount,
    couponDiscount = couponDiscount,
    amount = amount
)