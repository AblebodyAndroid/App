package com.smilehunter.ablebody.data.mapper

import com.smilehunter.ablebody.domain.model.LikeListData
import com.smilehunter.ablebody.network.model.response.CreatorDetailLikeUsersResponseData

fun CreatorDetailLikeUsersResponseData.toDomain() =
    LikeListData(
        uid = uid,
        nickname = nickname,
        userName = name,
        profileImageURL = profileUrl
    )