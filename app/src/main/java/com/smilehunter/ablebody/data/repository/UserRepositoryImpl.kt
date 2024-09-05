package com.smilehunter.ablebody.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.smilehunter.ablebody.data.mapper.asExternalModel
import com.smilehunter.ablebody.data.mapper.toDomain
import com.smilehunter.ablebody.domain.model.Gender
import com.smilehunter.ablebody.data.paging.UserBoardPagingSource
import com.smilehunter.ablebody.data.result.FileTooLargeException
import com.smilehunter.ablebody.datastore.DataStoreService
import com.smilehunter.ablebody.domain.model.CouponData
import com.smilehunter.ablebody.domain.model.DeliveryAddressData
import com.smilehunter.ablebody.domain.model.LocalUserInfoData
import com.smilehunter.ablebody.domain.model.UserBoardData
import com.smilehunter.ablebody.domain.model.UserInfoData
import com.smilehunter.ablebody.domain.repository.UserRepository
import com.smilehunter.ablebody.domain.usecase.CouponStatus
import com.smilehunter.ablebody.network.NetworkService
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher.IO
import com.smilehunter.ablebody.network.di.Dispatcher
import com.smilehunter.ablebody.network.model.AbleBodyResponse
import com.smilehunter.ablebody.network.model.request.ChangePhoneNumberRequest
import com.smilehunter.ablebody.network.model.request.EditProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkService: NetworkService,
    private val dataStoreService: DataStoreService,
) : UserRepository {

    override val localUserInfoData: Flow<LocalUserInfoData>
        get() = dataStoreService.getUserInfoData()
            .onEach { userInfoDataStore ->
                if (userInfoDataStore.uid.isEmpty()) {
                    networkService.getMyUserData().data?.apply {
                        updateLocalUserInfo {
                            it.copy(
                                uid = uid,
                                name = name,
                                nickname = nickname,
                                gender = when (gender) {
                                    Gender.MALE -> LocalUserInfoData.Gender.MALE
                                    Gender.FEMALE -> LocalUserInfoData.Gender.FEMALE
                                },
                                profileImageUrl = profileUrl
                            )
                        }
                    }
                }
            }
            .map { userInfoDataStore ->
                userInfoDataStore.asExternalModel()
            }

    override suspend fun getMyUserData(): UserInfoData =
        networkService.getMyUserData().data!!.toDomain()

    override suspend fun getUserData(uid: String): UserInfoData =
        networkService.getUserData(uid).data!!.toDomain()

    override suspend fun updateLocalUserInfo(localUserInfoData: (LocalUserInfoData) -> LocalUserInfoData) {
        dataStoreService.updateUserInfoData {
            val data = localUserInfoData(it.asExternalModel())
            it.toBuilder()
                .setUid(data.uid)
                .setName(data.name)
                .setNickname(data.nickname)
                .setGender(
                    when (data.gender) {
                        LocalUserInfoData.Gender.MALE -> com.smilehunter.ablebody.Gender.MALE
                        LocalUserInfoData.Gender.FEMALE -> com.smilehunter.ablebody.Gender.FEMALE
                        LocalUserInfoData.Gender.UNRECOGNIZED -> com.smilehunter.ablebody.Gender.UNRECOGNIZED
                    }
                )
                .setProfileImageUrl(data.profileImageUrl)
                .build()
        }
    }

    override suspend fun editProfile(
        editProfile: EditProfile,
        profileImageInputStream: InputStream?,
    ): Boolean {
        if (profileImageInputStream == null) {
            val response = networkService.editProfile(profile = editProfile, profileImage = null)
            dataStoreService.updateUserInfoData { it.toBuilder().clear().build() }
            return response.success
        }
        val temp = File.createTempFile("image_compress_file", ".jpeg")

        FileOutputStream(temp).buffered().use {
            val fileSize = profileImageInputStream.available()
            val bitmap = BitmapFactory.decodeStream(profileImageInputStream)

            val quality = when {
                fileSize < 1000000 -> 75
                fileSize < 2000000 -> 40
                fileSize < 4000000 -> 20
                else -> 1
            }

            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, it)
            bitmap.recycle()
        }

        if (temp.length() > 1000000) {
            throw FileTooLargeException()
        }

        val response = networkService.editProfile(profile = editProfile, profileImage = temp)
        temp.delete()
        temp.deleteOnExit()
        dataStoreService.updateUserInfoData { it.toBuilder().clear().build() }
        return response.success
    }

    override suspend fun getCouponBags(): List<CouponData> =
        networkService.getCouponBags().data!!.map { it.toDomain() }

    override suspend fun addCouponByCouponCode(couponCode: String): CouponStatus {
        val response = networkService.addCouponByCouponCode(couponCode)

        if (response.success) {
            return CouponStatus.SUCCESS
        }

        return when (response.errorCode) {
            AbleBodyResponse.ErrorCode.UNABLE_COUPON -> CouponStatus.UNABLE_COUPON
            AbleBodyResponse.ErrorCode.INVALID_COUPON_CODE -> CouponStatus.INVALID_COUPON_CODE
            AbleBodyResponse.ErrorCode.ALREADY_EXIST_COUPON -> CouponStatus.ALREADY_EXIST_COUPON
            else -> CouponStatus.UNKNOWN_ERROR
        }
    }

    override suspend fun getMyAddress(): DeliveryAddressData =
        networkService.getAddress().data!!.toDomain()

    override suspend fun addMyAddress(
        name: String,
        phoneNumber: String,
        roadAddress: String,
        roadDetailAddress: String,
        zipCode: String,
        deliveryRequestMessage: String,
    ) {
        networkService.addAddress(
            receiverName = name,
            phoneNum = phoneNumber,
            addressInfo = roadAddress,
            detailAddress = roadDetailAddress,
            zipCode = zipCode,
            deliveryRequest = deliveryRequestMessage
        )
    }

    override suspend fun editMyAddress(
        name: String,
        phoneNumber: String,
        roadAddress: String,
        roadDetailAddress: String,
        zipCode: String,
        deliveryRequestMessage: String,
    ) {
        networkService.editAddress(
            receiverName = name,
            phoneNum = phoneNumber,
            addressInfo = roadAddress,
            detailAddress = roadDetailAddress,
            zipCode = zipCode,
            deliveryRequest = deliveryRequestMessage
        )
    }

    override fun getMyBoard(uid: String?): Flow<PagingData<UserBoardData.Content>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            initialKey = 0
        ) {
            UserBoardPagingSource(uid = uid)
        }
            .flow
    }

    override suspend fun getUserAdConsent(): Boolean {
        return networkService.getUserAdConsent().data!!
    }

    override suspend fun acceptUserAdConsent(accept: Boolean): String {
        return networkService.acceptUserAdConsent(accept).data!!
    }

    override suspend fun changePhoneNumber(changePhoneNumberRequest: ChangePhoneNumberRequest): UserInfoData {
        return networkService.changePhoneNumber(changePhoneNumberRequest).data!!.toDomain()
    }

    override suspend fun resignUser(reason: String): String {
        return networkService.resignUser(reason).data!!
    }


    override suspend fun suggestApp(text: String) {
        networkService.suggestion(content = text)
    }
}