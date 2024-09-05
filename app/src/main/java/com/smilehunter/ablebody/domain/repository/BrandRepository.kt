package com.smilehunter.ablebody.domain.repository

import androidx.paging.PagingData
import com.smilehunter.ablebody.data.model.Gender
import com.smilehunter.ablebody.data.model.HomeCategory
import com.smilehunter.ablebody.data.model.ItemChildCategory
import com.smilehunter.ablebody.data.model.ItemGender
import com.smilehunter.ablebody.data.model.ItemParentCategory
import com.smilehunter.ablebody.data.model.SortingMethod
import com.smilehunter.ablebody.domain.model.BrandListData
import com.smilehunter.ablebody.domain.model.CodyItemData
import com.smilehunter.ablebody.domain.model.ProductItemData
import kotlinx.coroutines.flow.Flow

interface BrandRepository {

    suspend fun brandMain(sort: SortingMethod): List<BrandListData>

    suspend fun brandDetailItem(
        sort: SortingMethod,
        brandId: Long,
        itemGender: ItemGender,
        parentCategory: ItemParentCategory,
        childCategory: ItemChildCategory? = null,
    ): Flow<PagingData<ProductItemData.Item>>

    suspend fun brandDetailCody(
        brandId: Long,
        gender: List<Gender>,
        category: List<HomeCategory>,
        personHeightRangeStart: Int? = null,
        personHeightRangeEnd: Int? = null,
    ): Flow<PagingData<CodyItemData.Item>>
}