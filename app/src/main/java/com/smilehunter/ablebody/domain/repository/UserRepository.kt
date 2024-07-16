package com.smilehunter.ablebody.domain.repository

import com.smilehunter.ablebody.network.model.request.ChangePhoneNumberRequest
import com.smilehunter.ablebody.network.model.request.EditProfile
import com.smilehunter.ablebody.network.model.AddCouponResponse
import com.smilehunter.ablebody.network.model.GetAddressResponse
import com.smilehunter.ablebody.network.model.GetCouponBagsResponse
import com.smilehunter.ablebody.network.model.GetMyBoardResponse
import com.smilehunter.ablebody.network.model.UserDataResponse
import com.smilehunter.ablebody.domain.model.LocalUserInfoData
import kotlinx.coroutines.flow.Flow
import java.io.InputStream

interface UserRepository {

    suspend fun getMyUserData(): UserDataResponse

    suspend fun getUserData(uid: String): UserDataResponse

    val localUserInfoData: Flow<LocalUserInfoData>

    suspend fun updateLocalUserInfo(localUserInfoData: (LocalUserInfoData) -> LocalUserInfoData)

    suspend fun editProfile(
        editProfile: EditProfile,
        profileImageInputStream: InputStream?
    ): UserDataResponse

    suspend fun getMyBoard(
        uid: String? = null,
        page: Int = 0,
        size: Int = 10
    ): GetMyBoardResponse

    suspend fun getCouponBags(): GetCouponBagsResponse

    suspend fun addCouponByCouponCode(couponCode: String): AddCouponResponse

    suspend fun getMyAddress(): GetAddressResponse

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

    suspend fun changePhoneNumber(changePhoneNumberRequest: ChangePhoneNumberRequest): UserDataResponse

    suspend fun resignUser(reason: String): String

    suspend fun suggestApp(text: String)
}