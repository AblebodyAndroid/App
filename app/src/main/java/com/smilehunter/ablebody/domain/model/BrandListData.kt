package com.smilehunter.ablebody.domain.model

data class BrandListData(
    val name: String,
    val id: Long,
    val thumbnail: String,
    val subName: String,
    val brandGender: ItemGender,
    val maxDiscount: Int
)
