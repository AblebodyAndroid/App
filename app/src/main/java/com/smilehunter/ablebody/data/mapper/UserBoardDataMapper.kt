package com.smilehunter.ablebody.data.mapper

import com.smilehunter.ablebody.domain.model.UserBoardData
import com.smilehunter.ablebody.network.model.response.GetMyBoardResponseData

fun GetMyBoardResponseData.toDomain() = UserBoardData(
    content = content.map {
        UserBoardData.Content(
            id = it.id,
            imageURL = it.imageURL,
            createDate = it.createDate,
            comments = it.comments,
            likes = it.likes,
            views = it.views,
            isSingleImage = it.plural,
        )
    },
    totalPages = totalPages,
    last = last,
    number = number,
    first = first
)