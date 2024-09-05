package com.smilehunter.ablebody.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.smilehunter.ablebody.data.mapper.toDomain
import com.smilehunter.ablebody.domain.model.ItemChildCategory
import com.smilehunter.ablebody.domain.model.ItemGender
import com.smilehunter.ablebody.domain.model.ItemParentCategory
import com.smilehunter.ablebody.domain.model.SortingMethod
import com.smilehunter.ablebody.data.paging.CommonProductItemPagingSource
import com.smilehunter.ablebody.domain.model.ProductItemData
import com.smilehunter.ablebody.domain.repository.FindItemRepository
import com.smilehunter.ablebody.network.NetworkService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindItemRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : FindItemRepository {

    override fun findItem(
        sortingMethod: SortingMethod,
        itemGender: ItemGender,
        parentCategory: ItemParentCategory,
        childCategory: ItemChildCategory?
    ): Flow<PagingData<ProductItemData.Item>> {

        return Pager(
            config = PagingConfig(pageSize = 10),
            initialKey = 0
        ) {
            CommonProductItemPagingSource { pageIndex ->
                networkService.findItem(
                    sort = sortingMethod,
                    itemGender = itemGender,
                    parentCategory = childCategory?.parentCategory ?: parentCategory,
                    childCategory = childCategory,
                    page = pageIndex,
                ).body()!!.data!!.toDomain()
            }
        }
            .flow
    }

}