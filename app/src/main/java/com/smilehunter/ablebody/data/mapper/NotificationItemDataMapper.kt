package com.smilehunter.ablebody.data.mapper

import com.smilehunter.ablebody.domain.model.NotificationItemData
import com.smilehunter.ablebody.network.model.response.GetMyNotiResponseData

fun GetMyNotiResponseData.toDomain() = NotificationItemData(
    content = content.map {
        NotificationItemData.Content(
            id = it.id,
            senderProfileImageURL = it.from.profileUrl,
            senderNickname = it.from.nickname,
            createDate = it.createDate,
            text = it.content ?: it.body!!,
            uri = it.url,
            checked = it.checked,
        )
    },
    totalPages = totalPages,
    last = last,
    number = number,
    first = first
)