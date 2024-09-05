package com.smilehunter.ablebody.domain.repository

import androidx.paging.PagingData
import com.smilehunter.ablebody.domain.model.ItemChildCategory
import com.smilehunter.ablebody.domain.model.ItemGender
import com.smilehunter.ablebody.domain.model.ItemParentCategory
import com.smilehunter.ablebody.domain.model.SortingMethod
import com.smilehunter.ablebody.domain.model.ProductItemData
import kotlinx.coroutines.flow.Flow

interface FindItemRepository {

    fun findItem(
        sortingMethod: SortingMethod,
        itemGender: ItemGender,
        parentCategory: ItemParentCategory,
        childCategory: ItemChildCategory? = null,
    ): Flow<PagingData<ProductItemData.Item>>
}