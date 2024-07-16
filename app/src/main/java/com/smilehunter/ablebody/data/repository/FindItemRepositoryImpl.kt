package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.data.model.ItemChildCategory
import com.smilehunter.ablebody.data.model.ItemGender
import com.smilehunter.ablebody.data.model.ItemParentCategory
import com.smilehunter.ablebody.data.model.SortingMethod
import com.smilehunter.ablebody.network.model.FindItemResponse
import com.smilehunter.ablebody.network.NetworkService
import retrofit2.Response
import javax.inject.Inject

class FindItemRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
): FindItemRepository {
    override suspend fun findItem(
        sortingMethod: SortingMethod,
        itemGender: ItemGender,
        parentCategory: ItemParentCategory,
        childCategory: ItemChildCategory?,
        page: Int,
        size: Int
    ): Response<FindItemResponse> {
        return networkService.findItem(
            sort = sortingMethod,
            itemGender = itemGender,
            parentCategory = childCategory?.parentCategory ?: parentCategory,
            childCategory = childCategory,
            page = page,
            size = size
        )
    }

}