package com.smilehunter.ablebody.network.model

import com.smilehunter.ablebody.network.model.response.AddOrderListResponseData
import com.smilehunter.ablebody.network.model.response.BrandDetailCodyResponseData
import com.smilehunter.ablebody.network.model.response.BrandDetailItemResponseData
import com.smilehunter.ablebody.network.model.response.BrandMainResponseData
import com.smilehunter.ablebody.network.model.response.CreatorDetailCommentResponseData
import com.smilehunter.ablebody.network.model.response.CreatorDetailLikeUsersResponseData
import com.smilehunter.ablebody.network.model.response.CreatorDetailReplyResponseData
import com.smilehunter.ablebody.network.model.response.CreatorDetailResponseData
import com.smilehunter.ablebody.network.model.response.FCMTokenAndAppVersionUpdateResponseData
import com.smilehunter.ablebody.network.model.response.FindCodyResponseData
import com.smilehunter.ablebody.network.model.response.FindItemResponseData
import com.smilehunter.ablebody.network.model.response.GetAddressResponseData
import com.smilehunter.ablebody.network.model.response.GetCouponBagsResponseData
import com.smilehunter.ablebody.network.model.response.GetDeliveryInfoResponseData
import com.smilehunter.ablebody.network.model.response.GetMyBoardResponseData
import com.smilehunter.ablebody.network.model.response.GetMyNotiResponseData
import com.smilehunter.ablebody.network.model.response.GetOrderListDetailResponseData
import com.smilehunter.ablebody.network.model.response.GetOrderListResponseData
import com.smilehunter.ablebody.network.model.response.ItemResponseData
import com.smilehunter.ablebody.network.model.response.NewUserCreateResponseData
import com.smilehunter.ablebody.network.model.response.ReadBookmarkCodyData
import com.smilehunter.ablebody.network.model.response.ReadBookmarkItemData
import com.smilehunter.ablebody.network.model.response.RefreshTokenResponseData
import com.smilehunter.ablebody.network.model.response.SMSCheckResponseData
import com.smilehunter.ablebody.network.model.response.SMSSendResponseData
import com.smilehunter.ablebody.network.model.response.SearchCodyResponseData
import com.smilehunter.ablebody.network.model.response.SearchItemResponseData
import com.smilehunter.ablebody.network.model.response.UniSearchResponseData
import com.smilehunter.ablebody.network.model.response.UserDataResponseData
import com.smilehunter.ablebody.network.model.AbleBodyResponse

/** Onboarding **/

typealias StringResponse = AbleBodyResponse<String>
typealias SendSMSResponse = AbleBodyResponse<SMSSendResponseData>
typealias CheckSMSResponse = AbleBodyResponse<SMSCheckResponseData>
typealias NewUserCreateResponse = AbleBodyResponse<NewUserCreateResponseData>
typealias RefreshTokenResponse = AbleBodyResponse<RefreshTokenResponseData>
typealias FCMTokenAndAppVersionUpdateResponse = AbleBodyResponse<FCMTokenAndAppVersionUpdateResponseData>

/** Brand **/

typealias BrandMainResponse = AbleBodyResponse<List<BrandMainResponseData>>
typealias BrandDetailItemResponse = AbleBodyResponse<BrandDetailItemResponseData>
typealias BrandDetailCodyResponse = AbleBodyResponse<BrandDetailCodyResponseData>

/** Bookmark **/

typealias AddBookmarkItemResponse = AbleBodyResponse<String>
typealias ReadBookmarkItemResponse = AbleBodyResponse<ReadBookmarkItemData>
typealias DeleteBookmarkItemResponse = AbleBodyResponse<String>
typealias AddBookmarkCodyResponse = AbleBodyResponse<String>
typealias ReadBookmarkCodyResponse = AbleBodyResponse<ReadBookmarkCodyData>
typealias DeleteBookmarkCodyResponse = AbleBodyResponse<String>

/** Find **/

typealias FindItemResponse = AbleBodyResponse<FindItemResponseData>
typealias FindCodyResponse = AbleBodyResponse<FindCodyResponseData>

/** Search **/

typealias UniSearchResponse = AbleBodyResponse<UniSearchResponseData>
typealias SearchItemResponse = AbleBodyResponse<SearchItemResponseData>
typealias SearchCodyResponse = AbleBodyResponse<SearchCodyResponseData>

/** Notification **/

typealias GetMyNotiResponse = AbleBodyResponse<GetMyNotiResponseData>
typealias CheckMyNotiResponse = AbleBodyResponse<String>

/** Creator **/

typealias CreatorDetailResponse = AbleBodyResponse<CreatorDetailResponseData>
typealias CreatorDetailLikeResponse = AbleBodyResponse<Long>
typealias CreatorDetailLikeUsersResponse = AbleBodyResponse<List<CreatorDetailLikeUsersResponseData>>
typealias CreatorDetailCommentResponse = AbleBodyResponse<CreatorDetailCommentResponseData>
typealias CreatorDetailReplyResponse = AbleBodyResponse<CreatorDetailReplyResponseData>
typealias CreatorDetailDeleteCommentReplyResponse = AbleBodyResponse<Long>
typealias CreatorDetailDeleteResponse = AbleBodyResponse<Long>

/** Item **/

typealias ItemDetailResponse = AbleBodyResponse<ItemResponseData>

/** Address **/

typealias AddAddressResponse = AbleBodyResponse<String>
typealias GetAddressResponse = AbleBodyResponse<GetAddressResponseData>
typealias EditAddressResponse = AbleBodyResponse<String>

/** Coupon **/

typealias GetCouponBagsResponse = AbleBodyResponse<List<GetCouponBagsResponseData>>
typealias AddCouponResponse = AbleBodyResponse<GetCouponBagsResponseData>

/** OrderList **/

typealias AddOrderListResponse = AbleBodyResponse<AddOrderListResponseData>
typealias GetOrderListResponse = AbleBodyResponse<List<GetOrderListResponseData>>
typealias CancelOrderListResponse = AbleBodyResponse<String>
typealias GetDeliveryInfoResponse = AbleBodyResponse<GetDeliveryInfoResponseData>
typealias GetOrderListDetailResponse = AbleBodyResponse<GetOrderListDetailResponseData>
typealias TossPaymentSuccessResponse = AbleBodyResponse<String>
typealias TossPaymentFailResponse = AbleBodyResponse<String>

/** User **/
typealias UserDataResponse = AbleBodyResponse<UserDataResponseData>
typealias GetMyBoardResponse = AbleBodyResponse<GetMyBoardResponseData>
typealias SuggestionResponse = AbleBodyResponse<String>
typealias ResignUserResponse = AbleBodyResponse<String>

/** Agreement **/

typealias GetUserAdConsentResponse = AbleBodyResponse<Boolean>
typealias AcceptUserAdConsentResponse = AbleBodyResponse<String>

/** Manage **/

typealias ReportResponse = AbleBodyResponse<String>