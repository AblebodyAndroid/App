package com.smilehunter.ablebody.data.mapper

import com.smilehunter.ablebody.domain.model.BrandListData
import com.smilehunter.ablebody.network.model.response.BrandMainResponseData

fun BrandMainResponseData.toDomain() = BrandListData(
    name = name,
    id = id,
    thumbnail = thumbnail,
    subName = subName,
    brandGender = brandGender,
    maxDiscount = maxDiscount
)