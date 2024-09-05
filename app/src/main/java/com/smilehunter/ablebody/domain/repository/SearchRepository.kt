package com.smilehunter.ablebody.domain.repository

import androidx.paging.PagingData
import com.smilehunter.ablebody.domain.model.Gender
import com.smilehunter.ablebody.domain.model.HomeCategory
import com.smilehunter.ablebody.domain.model.ItemChildCategory
import com.smilehunter.ablebody.domain.model.ItemGender
import com.smilehunter.ablebody.domain.model.ItemParentCategory
import com.smilehunter.ablebody.domain.model.SortingMethod
import com.smilehunter.ablebody.domain.model.CodyItemData
import com.smilehunter.ablebody.domain.model.ProductItemData
import com.smilehunter.ablebody.domain.model.SearchHistoryQuery
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun uniSearch(): List<String>

    fun getSearchHistoryQueries(): Flow<List<SearchHistoryQuery>>

    suspend fun deleteAllSearchHistory()

    fun searchItem(
        sortingMethod: SortingMethod,
        keyword: String,
        itemGender: ItemGender,
        itemParentCategory: ItemParentCategory,
        itemChildCategory: ItemChildCategory?,
    ): Flow<PagingData<ProductItemData.Item>>

    fun searchCody(
        keyword: String,
        genders: List<Gender>,
        category: List<HomeCategory>,
        personHeightRangeStart: Int? = null,
        personHeightRangeEnd: Int? = null,
    ): Flow<PagingData<CodyItemData.Item>>
}