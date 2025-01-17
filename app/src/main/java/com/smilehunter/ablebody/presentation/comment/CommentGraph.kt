package com.smilehunter.ablebody.presentation.comment

import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.smilehunter.ablebody.domain.model.ErrorHandlerCode
import com.smilehunter.ablebody.domain.model.LikedLocations
import com.smilehunter.ablebody.presentation.comment.ui.CommentRoute

const val COMMENT_ROUTE = "comment_route"

fun NavController.navigateToCommentScreen(
    contentID: Long,
    navOptions: NavOptions? = null,
) {
    navigate(route = "$COMMENT_ROUTE/$contentID", navOptions = navOptions)
}

fun NavGraphBuilder.addCommentScreen(
    onErrorRequest: (ErrorHandlerCode) -> Unit,
    onBackRequest: () -> Unit,
    onUserProfileVisitRequest: (String) -> Unit,
    likeUsersViewOnRequest: (Long, LikedLocations) -> Unit,
    isBottomBarShow: (Boolean) -> Unit
) {

    composable(
        route = "$COMMENT_ROUTE/{content_id}",
        arguments = listOf(navArgument("content_id") { type = NavType.LongType }),
        deepLinks = listOf(
            NavDeepLink("ablebody-app://ablebody.im/home/creatorComment/{content_id}")
        ),
    ) {  backStackEntry ->

        CommentRoute(
            onErrorRequest = onErrorRequest,
            onBackRequest = onBackRequest,
            onUserProfileVisitRequest = onUserProfileVisitRequest,
            likeUsersViewOnRequest = likeUsersViewOnRequest
        )
        isBottomBarShow(false)
    }
}