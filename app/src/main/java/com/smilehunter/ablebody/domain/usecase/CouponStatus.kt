package com.smilehunter.ablebody.domain.usecase

enum class CouponStatus {
    SUCCESS,
    UNKNOWN_ERROR,
    INVALID_COUPON_CODE,
    ALREADY_EXIST_COUPON,
    UNABLE_COUPON,
}