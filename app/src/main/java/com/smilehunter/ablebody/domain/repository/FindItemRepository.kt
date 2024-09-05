package com.smilehunter.ablebody.domain.repository

import androidx.paging.PagingData
import com.smilehunter.ablebody.data.model.ItemChildCategory
import com.smilehunter.ablebody.data.model.ItemGender
import com.smilehunter.ablebody.data.model.ItemParentCategory
import com.smilehunter.ablebody.data.model.SortingMethod
import com.smilehunter.ablebody.domain.model.ProductItemData
import com.smilehunter.ablebody.network.model.FindItemResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface FindItemRepository {

    fun findItem(
        sortingMethod: SortingMethod,
        itemGender: ItemGender,
        parentCategory: ItemParentCategory,
        childCategory: ItemChildCategory? = null,
    ): Flow<PagingData<ProductItemData.Item>>
}