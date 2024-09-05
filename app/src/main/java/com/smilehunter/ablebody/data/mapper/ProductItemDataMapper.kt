package com.smilehunter.ablebody.data.mapper

import com.smilehunter.ablebody.domain.model.ProductItemData
import com.smilehunter.ablebody.network.model.response.BrandDetailItemResponseData
import com.smilehunter.ablebody.network.model.response.FindItemResponseData
import com.smilehunter.ablebody.network.model.response.ReadBookmarkItemData
import com.smilehunter.ablebody.network.model.response.SearchItemResponseData

fun SearchItemResponseData.toDomain() = ProductItemData(
    content = content.map {
        ProductItemData.Item(
            id = it.id,
            name = it.name,
            price = it.price,
            salePrice = it.salePrice,
            brandName = it.brandName,
            imageURL = it.image,
            isSingleImage = it.isPlural,
            url = it.url,
            avgStarRating = it.avgStarRating
        )
    },
    totalPages = totalPages,
    last = last,
    number = number,
    first = first
)

fun ReadBookmarkItemData.toDomain() = ProductItemData(
    content = content.map {
        ProductItemData.Item(
            id = it.id,
            name = it.name,
            price = it.price,
            salePrice = it.salePrice,
            brandName = it.brandName,
            imageURL = it.image,
            isSingleImage = it.isPlural,
            url = it.url,
            avgStarRating = it.avgStarRating
        )
    },
    totalPages = totalPages,
    last = last,
    number = number,
    first = first
)

fun FindItemResponseData.toDomain() = ProductItemData(
    content = content.map {
        ProductItemData.Item(
            id = it.id,
            name = it.name,
            price = it.price,
            salePrice = it.salePrice,
            brandName = it.brandName,
            imageURL = it.image,
            isSingleImage = it.isPlural,
            url = it.url,
            avgStarRating = it.avgStarRating
        )
    },
    totalPages = totalPages,
    last = last,
    number = number,
    first = first
)

fun BrandDetailItemResponseData.toDomain() = ProductItemData(
    content = content.map {
        ProductItemData.Item(
            id = it.id,
            name = it.name,
            price = it.price,
            salePrice = it.salePrice,
            brandName = it.brandName,
            imageURL = it.image,
            isSingleImage = it.isPlural,
            url = it.url,
            avgStarRating = it.avgStarRating
        )
    },
    totalPages = totalPages,
    last = last,
    number = number,
    first = first
)