package com.smilehunter.ablebody.presentation.main

import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.smilehunter.ablebody.domain.model.ErrorHandlerCode
import com.smilehunter.ablebody.presentation.brand_detail.addBrandDetailScreen
import com.smilehunter.ablebody.presentation.brand_detail.navigateToBrandDetailScreen
import com.smilehunter.ablebody.presentation.comment.addCommentScreen
import com.smilehunter.ablebody.presentation.comment.navigateToCommentScreen
import com.smilehunter.ablebody.presentation.creator_detail.addCreatorDetailScreen
import com.smilehunter.ablebody.presentation.creator_detail.navigateToCreatorDetail
import com.smilehunter.ablebody.presentation.delivery.deliveryScreen
import com.smilehunter.ablebody.presentation.delivery.navigateToDeliveryScreen
import com.smilehunter.ablebody.presentation.delivery.navigateToSearchPostCodeWebViewScreen
import com.smilehunter.ablebody.presentation.delivery.popBackStackForResult
import com.smilehunter.ablebody.presentation.delivery.searchPostCodeWebViewScreen
import com.smilehunter.ablebody.presentation.home.HomeRoute
import com.smilehunter.ablebody.presentation.home.addHomeGraph
import com.smilehunter.ablebody.presentation.home.bookmark.addBookmarkScreen
import com.smilehunter.ablebody.presentation.home.brand.addBrandScreen
import com.smilehunter.ablebody.presentation.home.cody.addCodyScreen
import com.smilehunter.ablebody.presentation.home.item.addItemScreen
import com.smilehunter.ablebody.presentation.home.my.addEditProfileGraph
import com.smilehunter.ablebody.presentation.home.my.addSelectProfileImageScreen
import com.smilehunter.ablebody.presentation.home.my.navigateToEditProfileGraph
import com.smilehunter.ablebody.presentation.home.my.navigateToSelectProfileImageScreen
import com.smilehunter.ablebody.presentation.home.my.selectProfileImageForResult
import com.smilehunter.ablebody.presentation.item_detail.addItemDetailGraph
import com.smilehunter.ablebody.presentation.item_detail.navigateToItemDetailGraph
import com.smilehunter.ablebody.presentation.item_review.addItemReviewScreen
import com.smilehunter.ablebody.presentation.item_review.navigateToItemReview
import com.smilehunter.ablebody.presentation.like_list.addLikeUserListScreen
import com.smilehunter.ablebody.presentation.like_list.navigateToLikeUserListScreen
import com.smilehunter.ablebody.presentation.notification.NotificationRoute
import com.smilehunter.ablebody.presentation.notification.addNotificationScreen
import com.smilehunter.ablebody.presentation.order_management.addOrderItemDetailScreen
import com.smilehunter.ablebody.presentation.order_management.addOrderManagementGraph
import com.smilehunter.ablebody.presentation.order_management.navigateToOrderItemDetailScreen
import com.smilehunter.ablebody.presentation.order_management.navigateToOrderManagementGraph
import com.smilehunter.ablebody.presentation.payment.addPaymentGraph
import com.smilehunter.ablebody.presentation.payment.navigateToPayment
import com.smilehunter.ablebody.presentation.receipt.addReceiptScreen
import com.smilehunter.ablebody.presentation.receipt.navigateToReceiptScreen
import com.smilehunter.ablebody.presentation.search.addSearchScreen

@Composable
fun MainNavHost(
    recreateRequest: () -> Unit,
    isBottomBarShow: (Boolean) -> Unit,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        enterTransition = { fadeIn(animationSpec = tween(500)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        addHomeGraph(
            isBottomBarShow = isBottomBarShow,
            settingOnClickRouteRequest = { navController.navigate("SettingScreen") },
            onBackRequest = navController::popBackStack,
            suggestonClick = { navController.navigate("SuggestScreen") },
            myInfoOnClick = { navController.navigate("MyInfoScreenRoute") },
            alarmOnClick = { navController.navigate("AlarmScreen") },
            withDrawOnClick = { navController.navigate("WithdrawBeforeScreen") },
            editButtonOnClick = { navController.navigate("MyInfomationEditScreen") },
            withDrawReasonOnClick = { navController.navigate("WithdrawScreenRoute/$it") },
            coupononClick = { navController.navigate("CouponRoute") },
            couponRegisterOnClick = { navController.navigate("CouponRegisterRoute") },
            onReport = { navController.navigate("ReportRoute/$it") },
            withDrawButtonOnClick = { navController.navigate("WithDrawCompleteScreen") },
            orderManagementOnClick = { navController.navigateToOrderManagementGraph() },
            onPositiveBtnClick = { navController.navigate("ChangePhoneNumberScreen") },
            certificationBtnOnClick = { navController.navigate("InputCertificationNumberRoute/$it") },
            onVerificationSuccess = { navController.navigate("MyInfomationEditScreen") },
            onProfileEditClick = {
                Log.d("MainNavHost", "프로필 편집 버튼 누름")
                navController.navigateToEditProfileGraph()
            },
            onErrorOccur = navController::navigateErrorHandlingScreen,
            nestedGraph = {
                addBrandScreen(
                    isBottomBarShow = isBottomBarShow,
                    onErrorRequest = navController::navigateErrorHandlingScreen,
                    onSearchBarClick = { navController.navigate("SearchRoute") },
                    onAlertButtonClick = { navController.navigate(NotificationRoute) },
                    onBrandDetailRouteRequest = navController::navigateToBrandDetailScreen
                )
                addItemScreen(
                    isBottomBarShow = isBottomBarShow,
                    onErrorRequest = navController::navigateErrorHandlingScreen,
                    onSearchBarClick = { navController.navigate("SearchRoute") },
                    onAlertButtonClick = { navController.navigate(NotificationRoute) },
                    onProductItemDetailRouteRequest = navController::navigateToItemDetailGraph,
                )
                addCodyScreen(
                    isBottomBarShow = isBottomBarShow,
                    onErrorRequest = navController::navigateErrorHandlingScreen,
                    onSearchBarClick = { navController.navigate("SearchRoute") },
                    onAlertButtonClick = { navController.navigate(NotificationRoute) },
                    onCodyItemDetailRouteRequest = navController::navigateToCreatorDetail,
                )
                addBookmarkScreen(
                    isBottomBarShow = isBottomBarShow,
                    onErrorRequest = navController::navigateErrorHandlingScreen,
                    onSearchBarClick = { navController.navigate("SearchRoute") },
                    onAlertButtonClick = { navController.navigate(NotificationRoute) },
                    onProductItemDetailRouteRequest = navController::navigateToItemDetailGraph,
                    onCodyItemDetailRouteRequest = navController::navigateToCreatorDetail,
                )
                addEditProfileGraph(
                    onBackRequest = navController::popBackStack,
                    defaultImageSelectableViewRequest = navController::navigateToSelectProfileImageScreen,
                    nestedGraph = {
                        addSelectProfileImageScreen(
                            onBackRequest = navController::popBackStack,
                            confirmButtonClick = navController::selectProfileImageForResult
                        )
                    }
                )
            }
        )

        addSearchScreen(
            isBottomBarShow = isBottomBarShow,
            onErrorOccur = navController::navigateErrorHandlingScreen,
            backRequest = navController::popBackStack,
            productItemClick = navController::navigateToItemDetailGraph,
            codyItemClick = navController::navigateToCreatorDetail,
        )

        addNotificationScreen(
            isBottomBarShow = isBottomBarShow,
            onErrorRequest = navController::navigateErrorHandlingScreen,
            onBackRequest = navController::popBackStack,
            itemClick = navController::navigateDeepLink
        )

        addBrandDetailScreen(
            isBottomBarShow = isBottomBarShow,
            onErrorRequest = navController::navigateErrorHandlingScreen,
            onBackRequest = navController::popBackStack,
            productItemClick = navController::navigateToItemDetailGraph,
            codyItemClick = navController::navigateToCreatorDetail,
        )

        addCreatorDetailScreen(
            isBottomBarShow = isBottomBarShow,
            onErrorRequest = navController::navigateErrorHandlingScreen,
            onBackRequest = navController::popBackStack,
            profileRequest = {
                navController.navigate("OtherNormalUserRoute/$it")
                Log.d("다른 유저 프로필", it)
            },
            commentButtonOnClick = navController::navigateToCommentScreen,
            likeCountButtonOnClick = navController::navigateToLikeUserListScreen,
            productItemOnClick = navController::navigateToItemDetailGraph
        )

        addLikeUserListScreen(
            isBottomBarShow = isBottomBarShow,
            onErrorRequest = navController::navigateErrorHandlingScreen,
            onBackRequest = navController::popBackStack,
            profileRequest = {
                navController.navigate("OtherNormalUserRoute/$it")
                Log.d("다른 유저 프로필", it)
            },
        )

        addCommentScreen(
            onErrorRequest = navController::navigateErrorHandlingScreen,
            onBackRequest = navController::popBackStack,
            onUserProfileVisitRequest = { /* TODO 다른 유저의 Profile 화면으로 가기 */ },
            likeUsersViewOnRequest = navController::navigateToLikeUserListScreen,
            isBottomBarShow = isBottomBarShow
        )

        addItemDetailGraph(
            isBottomBarShow = isBottomBarShow,
            onErrorOccur = navController::navigateErrorHandlingScreen,
            onBackRequest = navController::popBackStack,
            brandOnClick = navController::navigateToBrandDetailScreen,
            codyOnClick = navController::navigateToCreatorDetail,
            itemReview = navController::navigateToItemReview,
            purchaseOnClick = navController::navigateToPayment
        ) {
            addItemReviewScreen(
                isBottomShow = isBottomBarShow,
                onBackRequest = navController::popBackStack
            )
        }

        addPaymentGraph(
            isBottomBarShow = isBottomBarShow,
            onErrorOccur = navController::navigateErrorHandlingScreen,
            onBackRequest = navController::popBackStack,
            addressRequest = navController::navigateToDeliveryScreen,
            receiptRequest = navController::navigateToReceiptScreen,
            nestedGraphs = {
                deliveryScreen(
                    onBackRequest = navController::popBackStack,
                    postCodeRequest = navController::navigateToSearchPostCodeWebViewScreen,
                    isBottomBarShow = isBottomBarShow
                )
                searchPostCodeWebViewScreen(
                    onFinished = navController::popBackStackForResult,
                    isBottomBarShow = isBottomBarShow
                )
                addReceiptScreen(
                    onErrorOccur = navController::navigateErrorHandlingScreen,
                    orderComplete = recreateRequest
                )
            }
        )

        addOrderManagementGraph(
            isBottomBarShow = isBottomBarShow,
            onErrorOccur = navController::navigateErrorHandlingScreen,
            onBackRequest = navController::popBackStack,
            itemOnClick = navController::navigateToOrderItemDetailScreen,
            nestedGraphs = {
                addOrderItemDetailScreen(
                    isBottomBarShow = isBottomBarShow,
                    onErrorRequest = navController::navigateErrorHandlingScreen,
                    onBackRequest = navController::popBackStack,
                )
            }
        )

        addNotFoundErrorScreen(
            isBottomBarShow = isBottomBarShow,
            onClick = {
                navController.popBackStack()
                navController.popBackStack()
            }
        )

        addInternalServerErrorScreen(
            isBottomBarShow = isBottomBarShow,
            onClick = recreateRequest
        )
    }
}

private fun NavController.navigateDeepLink(uri: String) {
    try {
        navigate(deepLink = Uri.parse(uri))
    } catch (e: Exception) {
        navigateErrorHandlingScreen(ErrorHandlerCode.INTERNAL_SERVER_ERROR)
        e.printStackTrace()
    }
}