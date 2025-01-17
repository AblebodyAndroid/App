package com.smilehunter.ablebody.network

import com.smilehunter.ablebody.domain.model.ItemChildCategory
import com.smilehunter.ablebody.domain.model.ItemGender
import com.smilehunter.ablebody.domain.model.ItemParentCategory
import com.smilehunter.ablebody.domain.model.SortingMethod
import com.smilehunter.ablebody.network.model.request.AddOrderListRequest
import com.smilehunter.ablebody.network.model.request.AddressRequest
import com.smilehunter.ablebody.network.model.request.ChangePhoneNumberRequest
import com.smilehunter.ablebody.network.model.request.FCMTokenAndAppVersionUpdateRequest
import com.smilehunter.ablebody.network.model.request.NewUserCreateRequest
import com.smilehunter.ablebody.network.model.request.RefreshTokenRequest
import com.smilehunter.ablebody.network.model.request.ReportRequest
import com.smilehunter.ablebody.network.model.request.SMSCheckRequest
import com.smilehunter.ablebody.network.model.request.SMSSendRequest
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
import com.smilehunter.ablebody.network.model.ReadBookmarkCodyResponse
import com.smilehunter.ablebody.network.model.ReadBookmarkItemResponse
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
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkAPI {

    /** Onboarding **/

    @POST("/api/onboarding/send-sms")
    fun sendSMS(
        @Body smsSendRequest: SMSSendRequest
    ): Call<SendSMSResponse>

    @POST("/api/onboarding/check-sms")
    fun checkSMS(
        @Body smsCheckRequest: SMSCheckRequest
    ): Call<CheckSMSResponse>

    @GET("/api/onboarding/check-nickname/{nickname}")
    fun checkNickname(
        @Path(value = "nickname") nickname: String
    ): Call<StringResponse>

    @POST("/api/onboarding/new-user")
    fun createNewUser(
        @Body newUserCreateRequest: NewUserCreateRequest
    ): Call<NewUserCreateResponse>

    @POST("/api/onboarding/refresh")
    fun getRefreshToken(
        @Body refreshTokenRequest: RefreshTokenRequest
    ): Call<RefreshTokenResponse>

    @GET("/api/onboarding/dummy")
    fun getDummyToken(): Call<StringResponse>

    @POST("/api/onboarding/fcm-appVersion")
    fun updateFCMTokenAndAppVersion(
        @Body fcmTokenAndAppVersionUpdateRequest: FCMTokenAndAppVersionUpdateRequest
    ): Call<FCMTokenAndAppVersionUpdateResponse>

    /** Brand **/

    @GET("/api/brand/main")
    fun brandMain(
        @Query("sort") sort: SortingMethod
    ): Call<BrandMainResponse>

    @GET("/api/brand/detail/item")
    fun brandDetailItem(
        @Query("sort") sort: SortingMethod,
        @Query("brandId") brandId: Long,
        @Query("itemGender") itemGender: ItemGender,
        @Query("parentCategory") parentCategory: ItemParentCategory,
        @Query("childCategory") childCategory: ItemChildCategory?,
        @Query("page") page: Int?,
        @Query("size") size: Int?

    ): Call<BrandDetailItemResponse>

    @GET("/api/brand/detail/cody")
    fun brandDetailCody(
        @Query("brandId") brandId: Long,
        @Query("gender") gender: String,
        @Query("category") category: String,
        @Query("height1") personHeightRangeStart: Int?,
        @Query("height2") personHeightRangeEnd: Int?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
    ): Call<BrandDetailCodyResponse>

    /** Bookmark **/

    @POST("/api/bookmark/item")
    fun addBookmarkItem(
        @Query("itemId") itemID: Long
    ): Call<AddBookmarkItemResponse>

    @GET("/api/bookmark/item")
    fun readBookmarkItem(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Call<ReadBookmarkItemResponse>

    @DELETE("/api/bookmark/item")
    fun deleteBookmarkItem(
        @Query("itemId") itemID: Long
    ): Call<DeleteBookmarkItemResponse>

    @POST("/api/bookmark/cody")
    fun addBookmarkCody(
        @Query("codyId") itemID: Long
    ): Call<AddBookmarkCodyResponse>

    @GET("/api/bookmark/cody")
    fun readBookmarkCody(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Call<ReadBookmarkCodyResponse>

    @DELETE("/api/bookmark/cody")
    fun deleteBookmarkCody(
        @Query("codyId") codyID: Long
    ): Call<DeleteBookmarkCodyResponse>

    /** Find **/

    @GET("/api/find/new-item")
    fun findItem(
        @Query("sort") sort: SortingMethod,
        @Query("itemGender") itemGender: ItemGender,
        @Query("parentCategory") parentCategory: ItemParentCategory,
        @Query("childCategory") childCategory: ItemChildCategory? = null,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Call<FindItemResponse>

    @GET("/api/find/style")
    fun findCody(
        @Query("gender") genders: String,
        @Query("category") category: String,
        @Query("height1") personHeightRangeStart: Int?,
        @Query("height2") personHeightRangeEnd: Int?,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Call<FindCodyResponse>

    /** search **/

    @GET("/api/search/uni")
    suspend fun uniSearch(
        @Query("keyword") keyword: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10
    ): UniSearchResponse

    @GET("/api/search/item")
    suspend fun searchItem(
        @Query("sort") sort: SortingMethod,
        @Query("keyword") keyword: String,
        @Query("itemGender") itemGender: ItemGender,
        @Query("parentCategory") parentCategory: ItemParentCategory,
        @Query("childCategory") childCategory: ItemChildCategory? = null,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): SearchItemResponse

    @GET("/api/search/cody")
    suspend fun searchCody(
        @Query("keyword") keyword: String,
        @Query("gender") genders: String,
        @Query("category") category: String,
        @Query("height1") personHeightRangeStart: Int? = null,
        @Query("height2") personHeightRangeEnd: Int? = null,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): SearchCodyResponse

    /** notification **/

    @GET("/api/my/noti")
    suspend fun getMyNoti(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 30
    ): GetMyNotiResponse

    @POST("/api/my/noti")
    suspend fun checkMyNoti(
        @Query("id") id: Long
    ): CheckMyNotiResponse

    /** creator **/

    @GET("/api/home/detail")
    suspend fun creatorDetail(
        @Query("id") id: Long
    ): CreatorDetailResponse

    @POST("/api/home/unilike")
    suspend fun creatorDetailLike(
        @Query("where") where: String = "board",
        @Query("id") id: Long
    ): CreatorDetailLikeResponse

    @GET("/api/home/unilike")
    suspend fun creatorDetailLikeUsers(
        @Query("where") where: String = "board",
        @Query("id") id: Long
    ): CreatorDetailLikeUsersResponse

    @POST("/api/home/comment")
    suspend fun creatorDetailComment(
        @Query("id") id: Long,
        @Body content: String
    ): CreatorDetailCommentResponse

    @POST("/api/home/reply")
    suspend fun creatorDetailReply(
        @Query("id") id: Long,
        @Body content: String
    ): CreatorDetailReplyResponse

    @DELETE("/api/home/delete")
    suspend fun creatorDetailDeleteCommentReply(
        @Query("where") where: String,
        @Query("id") id: Long
    ): CreatorDetailDeleteCommentReplyResponse

    @DELETE("/api/home")
    suspend fun creatorDetailDelete(
        @Query("id") id: Long
    ): CreatorDetailDeleteResponse

    /** item **/
    @GET("/api/item")
    suspend fun itemDetail(
        @Query("id") id: Long
    ): ItemDetailResponse

    /** address **/

    @POST("/api/address")
    suspend fun addAddress(
        @Body address: AddressRequest
    ): AddAddressResponse

    @GET("/api/address")
    suspend fun getAddress(): GetAddressResponse

    @PUT("/api/address")
    suspend fun editAddress(
        @Body address: AddressRequest
    ): EditAddressResponse

    /** coupon **/

    @GET("/api/couponBags")
    suspend fun getCouponBags(): GetCouponBagsResponse

    @POST("/api/couponBags/add")
    suspend fun addCouponByCouponCode(
        @Query("couponCode") couponCode: String
    ): AddCouponResponse

    /** order **/

    @POST("/api/order")
    suspend fun addOrderList(
        @Body addOrderListRequest: AddOrderListRequest
    ): AddOrderListResponse

    @GET("/api/order")
    suspend fun getOrderList(): GetOrderListResponse

    @PUT("/api/order/cancel")
    suspend fun cancelOrderList(
        @Query("orderListId") orderListId: String
    ): CancelOrderListResponse

    @GET("/api/order/delivery-info")
    suspend fun getDeliveryInfo(
        @Query("orderListId") orderListId: String
    ): GetDeliveryInfoResponse

    @GET("/api/order/detail")
    suspend fun getOrderListDetail(
        @Query("orderListId") orderListId: String
    ): GetOrderListDetailResponse

    @GET("/api/toss/success")
    suspend fun tossPaymentSuccess(
        @Query("paymentKey") paymentKey: String,
        @Query("orderId") orderListId: String,
        @Query("amount") amount: String
    ): TossPaymentSuccessResponse

    @GET("/api/toss/fail")
    suspend fun tossPaymentFail(
        @Query("code") code: String,
        @Query("message") message: String,
        @Query("orderId") orderListId: String,
    ): TossPaymentFailResponse

    /** User **/

    @GET("/api/onboarding/splash")
    suspend fun getMyUserData(): UserDataResponse

    @GET("/api/my/user")
    suspend fun getUserData(
        @Query("uid") uid: String
    ): UserDataResponse

    @Multipart
    @PUT("/api/my/profile")
    suspend fun editProfile(
        @Part profile: MultipartBody.Part,
        @Part profileImage: MultipartBody.Part?
    ): UserDataResponse

    @GET("/api/my/home")
    suspend fun getMyBoard(
        @Query("uid") uid: String? = null,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10
    ): GetMyBoardResponse

    @PUT("/api/my/change-phone")
    suspend fun changePhoneNumber(
        @Body changePhoneNumberRequest: ChangePhoneNumberRequest
    ): UserDataResponse

    @DELETE("/api/my/user")
    suspend fun resignUser(
        @Query("reason") reason: String
    ): ResignUserResponse

    @POST("/api/my/suggestion")
    suspend fun suggestion(
        @Body content: String
    ): SuggestionResponse

    /** Agreement **/

    @GET("/api/my/consent-pushad")
    suspend fun getUserAdConsent(): GetUserAdConsentResponse

    @POST("/api/my/consent-pushad")
    suspend fun acceptUserAdConsent(
        @Query("accept") accept: Boolean
    ): AcceptUserAdConsentResponse

    /** Manage **/

    @POST("/api/report")
    suspend fun report(
        @Body reportRequest: ReportRequest
    ): ReportResponse
}