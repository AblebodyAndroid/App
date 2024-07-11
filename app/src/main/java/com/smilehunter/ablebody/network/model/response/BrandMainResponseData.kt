package com.smilehunter.ablebody.network.model.response

import com.google.gson.annotations.SerializedName

data class BrandMainResponseData(
    val name: String,
    val id: Long,
    val thumbnail: String,
    @SerializedName("sub_name") val subName: String,
    @SerializedName("brand_gender") val brandGender: com.smilehunter.ablebody.data.model.ItemGender,
    @SerializedName("max_discount") val maxDiscount: Int
)