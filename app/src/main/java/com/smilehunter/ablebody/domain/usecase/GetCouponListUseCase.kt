package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.data.repository.UserRepository
import com.smilehunter.ablebody.domain.model.CouponData
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import com.smilehunter.ablebody.network.model.response.GetCouponBagsResponseData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCouponListUseCase @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): List<CouponData> = withContext(ioDispatcher) {
        userRepository.getCouponBags().data!!.map { it.toDomain() }
    }
}

private fun GetCouponBagsResponseData.toDomain() =
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