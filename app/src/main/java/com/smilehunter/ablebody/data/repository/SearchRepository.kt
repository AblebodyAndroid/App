package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.data.model.Gender
import com.smilehunter.ablebody.data.model.HomeCategory
import com.smilehunter.ablebody.data.model.ItemChildCategory
import com.smilehunter.ablebody.data.model.ItemGender
import com.smilehunter.ablebody.data.model.ItemParentCategory
import com.smilehunter.ablebody.data.model.SortingMethod
import com.smilehunter.ablebody.network.model.SearchCodyResponse
import com.smilehunter.ablebody.network.model.SearchItemResponse
import com.smilehunter.ablebody.network.model.UniSearchResponse
import com.smilehunter.ablebody.model.SearchHistoryQuery
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun uniSearch(
        keyword: String,
        page: Int = 0,
        size: Int = 10
    ): UniSearchResponse

    fun getSearchHistoryQueries(): Flow<List<SearchHistoryQuery>>

    suspend fun deleteAllSearchHistory()
    suspend fun searchItem(
        sort: SortingMethod,
        keyword: String,
        itemGender: ItemGender,
        parentCategory: ItemParentCategory,
        childCategory: ItemChildCategory? = null,
        page: Int = 0,
        size: Int = 20
    ): SearchItemResponse

    suspend fun searchCody(
        keyword: String,
        genders: List<Gender>,
        category: List<HomeCategory>,
        personHeightRangeStart: Int? = null,
        personHeightRangeEnd: Int? = null,
        page: Int = 0,
        size: Int = 20
    ): SearchCodyResponse
}