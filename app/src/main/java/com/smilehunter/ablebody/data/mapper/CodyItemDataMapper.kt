package com.smilehunter.ablebody.data.mapper

import com.smilehunter.ablebody.domain.model.CodyItemData
import com.smilehunter.ablebody.network.model.response.BrandDetailCodyResponseData
import com.smilehunter.ablebody.network.model.response.FindCodyResponseData
import com.smilehunter.ablebody.network.model.response.ReadBookmarkCodyData
import com.smilehunter.ablebody.network.model.response.SearchCodyResponseData

fun BrandDetailCodyResponseData.toDomain() =
    CodyItemData(
        content = content.map {
            CodyItemData.Item(
                id = it.id,
                imageURL = it.imageURL,
                createDate = it.createDate,
                comments = it.comments,
                likes = it.likes,
                views = it.views,
                isSingleImage = !it.plural
            )
        },
        totalPages = totalPages,
        last = last,
        number = number,
        first = first
    )

fun FindCodyResponseData.toDomain() =
    CodyItemData(
        content = content.map {
            CodyItemData.Item(
                id = it.id,
                imageURL = it.imageURL,
                createDate = it.createDate,
                comments = it.comments,
                likes = it.likes,
                views = it.views,
                isSingleImage = !it.plural
            )
        },
        totalPages = totalPages,
        last = last,
        number = number,
        first = first
    )

fun ReadBookmarkCodyData.toDomain() =
    CodyItemData(
        content = content.map {
            CodyItemData.Item(
                id = it.id,
                imageURL = it.imageURL,
                createDate = it.createDate,
                comments = it.comments,
                likes = it.likes,
                views = it.views,
                isSingleImage = !it.plural
            )
        },
        totalPages = totalPages,
        last = last,
        number = number,
        first = first
    )

fun SearchCodyResponseData.toDomain() =
    CodyItemData(
        content = content.map {
            CodyItemData.Item(
                id = it.id,
                imageURL = it.imageURL,
                createDate = it.createDate,
                comments = it.comments,
                likes = it.likes,
                views = it.views,
                isSingleImage = !it.plural
            )
        },
        totalPages = totalPages,
        last = last,
        number = number,
        first = first
    )