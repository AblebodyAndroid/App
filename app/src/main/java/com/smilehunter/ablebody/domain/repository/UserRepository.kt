package com.smilehunter.ablebody.domain.repository

import com.smilehunter.ablebody.domain.model.CouponData
import com.smilehunter.ablebody.domain.model.DeliveryAddressData
import com.smilehunter.ablebody.domain.model.LocalUserInfoData
import com.smilehunter.ablebody.domain.model.UserInfoData
import com.smilehunter.ablebody.domain.usecase.CouponStatus
import com.smilehunter.ablebody.network.model.GetMyBoardResponse
import com.smilehunter.ablebody.network.model.request.ChangePhoneNumberRequest
import com.smilehunter.ablebody.network.model.request.EditProfile
import kotlinx.coroutines.flow.Flow
import java.io.InputStream

interface UserRepository {

    val localUserInfoData: Flow<LocalUserInfoData>

    suspend fun getMyUserData(): UserInfoData

    suspend fun getUserData(uid: String): UserInfoData

    suspend fun updateLocalUserInfo(localUserInfoData: (LocalUserInfoData) -> LocalUserInfoData)

    suspend fun editProfile(
        editProfile: EditProfile,
        profileImageInputStream: InputStream?
    ): Boolean

    suspend fun getMyBoard(
        uid: String? = null,
        page: Int = 0,
        size: Int = 10
    ): GetMyBoardResponse

    suspend fun getCouponBags(): List<CouponData>

    suspend fun addCouponByCouponCode(couponCode: String): CouponStatus

    suspend fun getMyAddress(): DeliveryAddressData

    suspend fun addMyAddress(
        name: String,
        phoneNumber: String,
        roadAddress: String,
        roadDetailAddress: String,
        zipCode: String,
        deliveryRequestMessage: String
    )

    suspend fun editMyAddress(
        name: String,
        phoneNumber: String,
        roadAddress: String,
        roadDetailAddress: String,
        zipCode: String,
        deliveryRequestMessage: String
    )

    suspend fun getUserAdConsent(): Boolean

    suspend fun acceptUserAdConsent(accept: Boolean): String

    suspend fun changePhoneNumber(changePhoneNumberRequest: ChangePhoneNumberRequest): UserInfoData

    suspend fun resignUser(reason: String): String

    suspend fun suggestApp(text: String)
}