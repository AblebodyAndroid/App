package com.smilehunter.ablebody.domain.model

import kotlin.math.roundToInt


data class ProductItemData(
    val content: List<Item>,
    val totalPages: Int,
    val last: Boolean,
    val number: Int,
    val first: Boolean,
) {
    data class Item(
        val id: Long,
        val name: String,
        val price: Int,
        val salePrice: Int?,
        val brandName: String,
        val imageURL: String,
        val isSingleImage: Boolean,
        val url: String,
        val avgStarRating: String?
    ) {
        val salePercentage = salePrice?.let { ((price - salePrice).toDouble() / price * 100).roundToInt() }
    }
}
