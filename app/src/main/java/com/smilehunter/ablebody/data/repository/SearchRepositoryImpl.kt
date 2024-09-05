package com.smilehunter.ablebody.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.smilehunter.ablebody.data.mapper.toDomain
import com.smilehunter.ablebody.domain.model.Gender
import com.smilehunter.ablebody.domain.model.HomeCategory
import com.smilehunter.ablebody.domain.model.ItemChildCategory
import com.smilehunter.ablebody.domain.model.ItemGender
import com.smilehunter.ablebody.domain.model.ItemParentCategory
import com.smilehunter.ablebody.domain.model.SortingMethod
import com.smilehunter.ablebody.data.paging.CommonCodyItemPagingSource
import com.smilehunter.ablebody.data.paging.CommonProductItemPagingSource
import com.smilehunter.ablebody.database.dao.SearchHistoryDao
import com.smilehunter.ablebody.database.model.SearchHistoryEntity
import com.smilehunter.ablebody.domain.model.CodyItemData
import com.smilehunter.ablebody.domain.model.ProductItemData
import com.smilehunter.ablebody.domain.model.SearchHistoryQuery
import com.smilehunter.ablebody.domain.model.asExternalModel
import com.smilehunter.ablebody.domain.repository.SearchRepository
import com.smilehunter.ablebody.network.NetworkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val searchHistoryDao: SearchHistoryDao
) : SearchRepository {

    override suspend fun uniSearch(): List<String> {
        return networkService.uniSearch("").data!!.recommendKeyword.creator
    }

    override fun getSearchHistoryQueries(): Flow<List<SearchHistoryQuery>> =
        searchHistoryDao.getAll().map { searchHistoryQueries ->
            searchHistoryQueries.map { it.asExternalModel() }
        }

    override suspend fun deleteAllSearchHistory() {
        searchHistoryDao.deleteAll()
    }

    override fun searchItem(
        sortingMethod: SortingMethod,
        keyword: String,
        itemGender: ItemGender,
        itemParentCategory: ItemParentCategory,
        itemChildCategory: ItemChildCategory?,
    ): Flow<PagingData<ProductItemData.Item>> {
        searchHistoryDao.insert(SearchHistoryEntity(keyword, System.currentTimeMillis()))

        return Pager(
            config = PagingConfig(pageSize = 10),
            initialKey = 0
        ) {
            CommonProductItemPagingSource { pageIndex ->
                networkService.searchItem(
                    sort = sortingMethod,
                    keyword = keyword,
                    itemGender = itemGender,
                    parentCategory = itemParentCategory,
                    childCategory = itemChildCategory,
                    page = pageIndex
                )
                    .data!!.toDomain()
            }
        }
            .flow
    }

    override fun searchCody(
        keyword: String,
        genders: List<Gender>,
        category: List<HomeCategory>,
        personHeightRangeStart: Int?,
        personHeightRangeEnd: Int?,
    ): Flow<PagingData<CodyItemData.Item>> {
        searchHistoryDao.insert(SearchHistoryEntity(keyword, System.currentTimeMillis()))

        return Pager(
            config = PagingConfig(pageSize = 10),
            initialKey = 0
        ) {
            CommonCodyItemPagingSource { pageIndex ->
                networkService.searchCody(
                    keyword = keyword,
                    genders = genders,
                    category = category,
                    personHeightRangeStart = personHeightRangeStart,
                    personHeightRangeEnd = personHeightRangeEnd,
                    page = pageIndex
                )
                    .data!!.toDomain()
            }
        }
            .flow
    }
}