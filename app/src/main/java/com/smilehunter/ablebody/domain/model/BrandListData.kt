package com.smilehunter.ablebody.domain.model

import com.smilehunter.ablebody.data.model.ItemGender

data class BrandListData(
    val name: String,
    val id: Long,
    val thumbnail: String,
    val subName: String,
    val brandGender: ItemGender,
    val maxDiscount: Int
)
