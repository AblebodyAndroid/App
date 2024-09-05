package com.smilehunter.ablebody.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.smilehunter.ablebody.data.mapper.toDomain
import com.smilehunter.ablebody.data.model.Gender
import com.smilehunter.ablebody.data.model.HomeCategory
import com.smilehunter.ablebody.data.model.ItemChildCategory
import com.smilehunter.ablebody.data.model.ItemGender
import com.smilehunter.ablebody.data.model.ItemParentCategory
import com.smilehunter.ablebody.data.model.SortingMethod
import com.smilehunter.ablebody.data.paging.CommonCodyItemPagingSource
import com.smilehunter.ablebody.data.paging.CommonProductItemPagingSource
import com.smilehunter.ablebody.domain.model.BrandListData
import com.smilehunter.ablebody.domain.model.CodyItemData
import com.smilehunter.ablebody.domain.model.ProductItemData
import com.smilehunter.ablebody.domain.repository.BrandRepository
import com.smilehunter.ablebody.network.NetworkService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BrandRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : BrandRepository {

    override suspend fun brandMain(sort: SortingMethod): List<BrandListData> {
        return networkService.brandMain(sort).body()?.data?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun brandDetailItem(
        sort: SortingMethod,
        brandId: Long,
        itemGender: ItemGender,
        parentCategory: ItemParentCategory,
        childCategory: ItemChildCategory?,
    ): Flow<PagingData<ProductItemData.Item>> {

        return Pager(
            config = PagingConfig(pageSize = 10),
            initialKey = 0
        ) {
            CommonProductItemPagingSource { pageIndex ->
                networkService.brandDetailItem(
                    sort = sort,
                    brandId = brandId,
                    itemGender = itemGender,
                    parentCategory = childCategory?.parentCategory ?: parentCategory,
                    childCategory = childCategory,
                    page = pageIndex
                ).body()!!.data!!.toDomain()
            }
        }
            .flow
    }


    override suspend fun brandDetailCody(
        brandId: Long,
        gender: List<Gender>,
        category: List<HomeCategory>,
        personHeightRangeStart: Int?,
        personHeightRangeEnd: Int?,
    ): Flow<PagingData<CodyItemData.Item>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            initialKey = 0
        ) {
            CommonCodyItemPagingSource {
                networkService.brandDetailCody(
                    brandId = brandId,
                    gender = gender,
                    category = category,
                    personHeightRangeStart = personHeightRangeStart,
                    personHeightRangeEnd = personHeightRangeEnd,
                    page = it,
                ).body()!!.data!!.toDomain()
            }
        }
            .flow
    }
}