package com.smilehunter.ablebody.domain.model

data class OrderRequestItem(
    val itemId: Int,
    val couponBagsId: Int?,
    val colorOption: String?,
    val sizeOption: String?,
    val itemPrice: Int,
    val itemCount: Int,
    val itemDiscount: Int,
    val couponDiscount: Int,
    val amount: Int
)
