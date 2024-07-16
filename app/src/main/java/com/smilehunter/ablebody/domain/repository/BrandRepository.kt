package com.smilehunter.ablebody.domain.repository

import com.smilehunter.ablebody.data.model.Gender
import com.smilehunter.ablebody.data.model.HomeCategory
import com.smilehunter.ablebody.data.model.ItemChildCategory
import com.smilehunter.ablebody.data.model.ItemGender
import com.smilehunter.ablebody.data.model.ItemParentCategory
import com.smilehunter.ablebody.data.model.SortingMethod
import com.smilehunter.ablebody.network.model.BrandDetailCodyResponse
import com.smilehunter.ablebody.network.model.BrandDetailItemResponse
import com.smilehunter.ablebody.network.model.BrandMainResponse
import retrofit2.Response

interface BrandRepository {
    suspend fun brandMain(sort: SortingMethod): Response<BrandMainResponse>

    suspend fun brandDetailItem(
        sort: SortingMethod,
        brandId: Long,
        itemGender: ItemGender,
        parentCategory: ItemParentCategory,
        childCategory: ItemChildCategory? = null,
        page: Int? = 0,
        size: Int? = 20
    ): Response<BrandDetailItemResponse>

    suspend fun brandDetailCody(
        brandId: Long,
        gender: List<Gender>,
        category: List<HomeCategory>,
        personHeightRangeStart: Int? = null,
        personHeightRangeEnd: Int? = null,
        page: Int? = 0,
        size: Int? = 20
    ): Response<BrandDetailCodyResponse>
}