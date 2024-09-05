package com.smilehunter.ablebody.data.mapper

import com.smilehunter.ablebody.domain.model.CouponData
import com.smilehunter.ablebody.network.model.response.GetCouponBagsResponseData

fun GetCouponBagsResponseData.toDomain() =
    CouponData(
        id = id,
        brand = brand,
        couponTitle = couponTitle,
        content = content,
        invalid = hasUsed == GetCouponBagsResponseData.CouponUsed.USED,
        couponType = when (couponType) {
            GetCouponBagsResponseData.CouponType.USER -> CouponData.CouponType.USER
            GetCouponBagsResponseData.CouponType.BRAND -> CouponData.CouponType.BRAND
        },
        discountType = when (discountType) {
            GetCouponBagsResponseData.DiscountType.PRICE -> CouponData.DiscountType.PRICE
            GetCouponBagsResponseData.DiscountType.RATE -> CouponData.DiscountType.RATE
        },
        expirationDate = expirationDateStr,
        couponCount = couponCount,
        discountAmount = discountAmount
    )