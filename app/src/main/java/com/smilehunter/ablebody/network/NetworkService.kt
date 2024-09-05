package com.smilehunter.ablebody.network

import com.smilehunter.ablebody.domain.model.Gender
import com.smilehunter.ablebody.domain.model.HomeCategory
import com.smilehunter.ablebody.domain.model.ItemChildCategory
import com.smilehunter.ablebody.domain.model.ItemGender
import com.smilehunter.ablebody.domain.model.ItemParentCategory
import com.smilehunter.ablebody.domain.model.SortingMethod
import com.smilehunter.ablebody.network.model.request.AddOrderListRequest
import com.smilehunter.ablebody.network.model.request.ChangePhoneNumberRequest
import com.smilehunter.ablebody.network.model.request.EditProfile
import com.smilehunter.ablebody.network.model.request.ReportRequest
import com.smilehunter.ablebody.network.model.AbleBodyResponse
import com.smilehunter.ablebody.network.model.AcceptUserAdConsentResponse
import com.smilehunter.ablebody.network.model.AddAddressResponse
import com.smilehunter.ablebody.network.model.AddBookmarkCodyResponse
import com.smilehunter.ablebody.network.model.AddBookmarkItemResponse
import com.smilehunter.ablebody.network.model.AddCouponResponse
import com.smilehunter.ablebody.network.model.AddOrderListResponse
import com.smilehunter.ablebody.network.model.BrandDetailCodyResponse
import com.smilehunter.ablebody.network.model.BrandDetailItemResponse
import com.smilehunter.ablebody.network.model.BrandMainResponse
import com.smilehunter.ablebody.network.model.CancelOrderListResponse
import com.smilehunter.ablebody.network.model.CheckMyNotiResponse
import com.smilehunter.ablebody.network.model.CheckSMSResponse
import com.smilehunter.ablebody.network.model.CreatorDetailCommentResponse
import com.smilehunter.ablebody.network.model.CreatorDetailDeleteCommentReplyResponse
import com.smilehunter.ablebody.network.model.CreatorDetailDeleteResponse
import com.smilehunter.ablebody.network.model.CreatorDetailLikeResponse
import com.smilehunter.ablebody.network.model.CreatorDetailLikeUsersResponse
import com.smilehunter.ablebody.network.model.CreatorDetailReplyResponse
import com.smilehunter.ablebody.network.model.CreatorDetailResponse
import com.smilehunter.ablebody.network.model.DeleteBookmarkCodyResponse
import com.smilehunter.ablebody.network.model.DeleteBookmarkItemResponse
import com.smilehunter.ablebody.network.model.EditAddressResponse
import com.smilehunter.ablebody.network.model.FCMTokenAndAppVersionUpdateResponse
import com.smilehunter.ablebody.network.model.FindCodyResponse
import com.smilehunter.ablebody.network.model.FindItemResponse
import com.smilehunter.ablebody.network.model.GetAddressResponse
import com.smilehunter.ablebody.network.model.GetCouponBagsResponse
import com.smilehunter.ablebody.network.model.GetDeliveryInfoResponse
import com.smilehunter.ablebody.network.model.GetMyBoardResponse
import com.smilehunter.ablebody.network.model.GetMyNotiResponse
import com.smilehunter.ablebody.network.model.GetOrderListDetailResponse
import com.smilehunter.ablebody.network.model.GetOrderListResponse
import com.smilehunter.ablebody.network.model.GetUserAdConsentResponse
import com.smilehunter.ablebody.network.model.ItemDetailResponse
import com.smilehunter.ablebody.network.model.NewUserCreateResponse
import com.smilehunter.ablebody.network.model.RefreshTokenResponse
import com.smilehunter.ablebody.network.model.ReportResponse
import com.smilehunter.ablebody.network.model.ResignUserResponse
import com.smilehunter.ablebody.network.model.SearchCodyResponse
import com.smilehunter.ablebody.network.model.SearchItemResponse
import com.smilehunter.ablebody.network.model.SendSMSResponse
import com.smilehunter.ablebody.network.model.StringResponse
import com.smilehunter.ablebody.network.model.SuggestionResponse
import com.smilehunter.ablebody.network.model.TossPaymentFailResponse
import com.smilehunter.ablebody.network.model.TossPaymentSuccessResponse
import com.smilehunter.ablebody.network.model.UniSearchResponse
import com.smilehunter.ablebody.network.model.UserDataResponse
import com.smilehunter.ablebody.network.model.response.ReadBookmarkCodyData
import com.smilehunter.ablebody.network.model.response.ReadBookmarkItemData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import java.io.File

interface NetworkService {

    /** Onboarding **/

    suspend fun sendSMS(phoneNumber: String, isNotTestMessage: Boolean = true): Response<SendSMSResponse>

    suspend fun checkSMS(phoneConfirmId: Long, verifyingCode: String): Response<CheckSMSResponse>

    suspend fun checkNickname(nickname: String): Response<StringResponse>

    suspend fun createNewUser(
        gender: Gender,
        nickname: String,
        profileImage: Int,
        verifyingCode: String,
        agreeRequiredConsent: Boolean,
        agreeMarketingConsent: Boolean
    ): Response<NewUserCreateResponse>

    suspend fun getRefreshToken(refreshToken: String): Response<RefreshTokenResponse>

    suspend fun getDummyToken(): Response<StringResponse>

    suspend fun updateFCMTokenAndAppVersion(
        fcmToken: String,
        appVersion: String
    ): Response<FCMTokenAndAppVersionUpdateResponse>


    /** Brand **/

    suspend fun brandMain(sort: SortingMethod): Response<BrandMainResponse>

    suspend fun brandDetailItem(
        sort: SortingMethod,
        brandId: Long,
        itemGender: ItemGender,
        parentCategory: ItemParentCategory,
        childCategory: ItemChildCategory? = null,
        page: Int? = 0,
        size: Int? = 20
    ): Response<BrandDetailItemResponse>

    suspend fun brandDetailCody(
        brandId: Long,
        gender: List<Gender>,
        category: List<HomeCategory>,
        personHeightRangeStart: Int? = null,
        personHeightRangeEnd: Int? = null,
        page: Int? = 0,
        size: Int? = 20
    ): Response<BrandDetailCodyResponse>

    /** Bookmark **/

    suspend fun addBookmarkItem(itemID: Long): Response<AddBookmarkItemResponse>

    suspend fun readBookmarkItem(
        page: Int = 0,
        size: Int = 20
    ): Response<AbleBodyResponse<ReadBookmarkItemData>>

    suspend fun deleteBookmarkItem(itemID: Long): Response<DeleteBookmarkItemResponse>

    suspend fun addBookmarkCody(itemID: Long): Response<AddBookmarkCodyResponse>

    suspend fun readBookmarkCody(
        page: Int = 0,
        size: Int = 20
    ): Response<AbleBodyResponse<ReadBookmarkCodyData>>

    suspend fun deleteBookmarkCody(itemID: Long): Response<DeleteBookmarkCodyResponse>

    /** Find **/

    suspend fun findItem(
        sort: SortingMethod,
        itemGender: ItemGender,
        parentCategory: ItemParentCategory,
        childCategory: ItemChildCategory? = null,
        page: Int = 0,
        size: Int = 20
    ): Response<FindItemResponse>

    suspend fun findCody(
        genders: List<Gender>,
        category: List<HomeCategory>,
        personHeightRangeStart: Int? = null,
        personHeightRangeEnd: Int? = null,
        page: Int = 0,
        size: Int = 20
    ): Response<FindCodyResponse>


    /** Search **/

    suspend fun uniSearch(
        keyword: String,
        page: Int = 0,
        size: Int = 10
    ): UniSearchResponse

    suspend fun searchItem(
        sort: SortingMethod,
        keyword: String,
        itemGender: ItemGender,
        parentCategory: ItemParentCategory,
        childCategory: ItemChildCategory? = null,
        page: Int = 0,
        size: Int = 20
    ): SearchItemResponse

    suspend fun searchCody(
        keyword: String,
        genders: List<Gender>,
        category: List<HomeCategory>,
        personHeightRangeStart: Int? = null,
        personHeightRangeEnd: Int? = null,
        page: Int = 0,
        size: Int = 20
    ): SearchCodyResponse

    /** Notification **/

    suspend fun getMyNoti(
        page: Int = 0,
        size: Int = 30
    ): GetMyNotiResponse

    suspend fun checkMyNoti(
        id: Long
    ): CheckMyNotiResponse

    /** Creator **/

    suspend fun creatorDetail(
        id: Long
    ): CreatorDetailResponse

    suspend fun creatorDetailLikeBoard(
        id: Long
    ): CreatorDetailLikeResponse

    suspend fun creatorDetailLikeComment(
        id: Long
    ): CreatorDetailLikeResponse

    suspend fun creatorDetailLikeReply(
        id: Long
    ): CreatorDetailLikeResponse

    suspend fun creatorDetailLikeUsersBoard(
        id: Long
    ): CreatorDetailLikeUsersResponse

    suspend fun creatorDetailLikeUsersComment(
        id: Long
    ): CreatorDetailLikeUsersResponse

    suspend fun creatorDetailLikeUsersReply(
        id: Long
    ): CreatorDetailLikeUsersResponse

    suspend fun creatorDetailComment(
        id: Long,
        content: String
    ): CreatorDetailCommentResponse

    suspend fun creatorDetailReply(
        id: Long,
        content: String
    ): CreatorDetailReplyResponse

    suspend fun creatorDetailDeleteComment(
        id: Long
    ): CreatorDetailDeleteCommentReplyResponse

    suspend fun creatorDetailDeleteReply(
        id: Long
    ): CreatorDetailDeleteCommentReplyResponse

    suspend fun creatorDetailDelete(
        id: Long
    ): CreatorDetailDeleteResponse

    /** item **/

    suspend fun itemDetail(
        id: Long
    ): ItemDetailResponse

    /** address **/

    suspend fun addAddress(
        receiverName: String,
        phoneNum: String,
        addressInfo: String,
        detailAddress: String,
        zipCode: String,
        deliveryRequest: String
    ): AddAddressResponse

    suspend fun getAddress(): GetAddressResponse

    suspend fun editAddress(
        receiverName: String,
        phoneNum: String,
        addressInfo: String,
        detailAddress: String,
        zipCode: String,
        deliveryRequest: String
    ): EditAddressResponse

    /** coupon **/

    suspend fun getCouponBags(): GetCouponBagsResponse

    suspend fun addCouponByCouponCode(couponCode: String): AddCouponResponse

    /** order **/

    suspend fun addOrderList(
        addOrderListRequest: AddOrderListRequest
    ): AddOrderListResponse

    suspend fun getOrderList(): GetOrderListResponse

    suspend fun cancelOrderList(
        id: String
    ): CancelOrderListResponse

    suspend fun getDeliveryInfo(
        id: String
    ): GetDeliveryInfoResponse

    suspend fun getOrderListDetail(
        id: String
    ): GetOrderListDetailResponse

    suspend fun tossPaymentSuccess(
        paymentKey: String,
        orderListId: String,
        amount: String
    ): TossPaymentSuccessResponse

    suspend fun tossPaymentFail(
        code: String,
        message: String,
        orderListId: String,
    ): TossPaymentFailResponse

    /** User **/
    suspend fun getMyUserData(): UserDataResponse

    suspend fun getUserData(uid: String): UserDataResponse

    suspend fun editProfile(
        profile: EditProfile,
        profileImage: File?,
    ): UserDataResponse

    suspend fun getMyBoard(
        uid: String? = null,
        page: Int = 0,
        size: Int = 10
    ): GetMyBoardResponse

    suspend fun changePhoneNumber(
        changePhoneNumberRequest: ChangePhoneNumberRequest
    ): UserDataResponse

    suspend fun resignUser(
        reason: String
    ): ResignUserResponse

    suspend fun suggestion(
        content: String
    ): SuggestionResponse

    /** Agreement **/

    @GET("/api/my/consent-pushad")
    suspend fun getUserAdConsent(): GetUserAdConsentResponse

    @POST("/api/my/consent-pushad")
    suspend fun acceptUserAdConsent(accept: Boolean): AcceptUserAdConsentResponse

    /** Manage **/

    suspend fun report(
        reportRequest: ReportRequest
    ): ReportResponse
}