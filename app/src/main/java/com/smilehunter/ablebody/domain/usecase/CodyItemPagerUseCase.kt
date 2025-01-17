package com.smilehunter.ablebody.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smilehunter.ablebody.data.mapper.toDomain
import com.smilehunter.ablebody.domain.model.Gender
import com.smilehunter.ablebody.domain.model.HomeCategory
import com.smilehunter.ablebody.domain.model.CodyItemData
import com.smilehunter.ablebody.network.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CodyItemPagerUseCase @Inject constructor(
    private val networkService: NetworkService
) {
    operator fun invoke(
        codyPagingSourceData: CodyPagingSourceData
    ): Flow<PagingData<CodyItemData.Item>> =
        Pager(
            config = PagingConfig(pageSize = 10),
            initialKey = 0
        ) {
            CodyPagingSource(codyPagingSourceData)
        }
            .flow

    private inner class CodyPagingSource(
        private val codyPagingSourceData: CodyPagingSourceData
    ) : PagingSource<Int, CodyItemData.Item>() {
        override fun getRefreshKey(state: PagingState<Int, CodyItemData.Item>): Int? {
            return state.anchorPosition
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CodyItemData.Item> {
            return try {
                val currentPageIndex = params.key ?: 0
                val codyItemData: CodyItemData = withContext(Dispatchers.IO) {
                    when (codyPagingSourceData) {
                        is CodyPagingSourceData.Brand -> {
                            networkService.brandDetailCody(
                                codyPagingSourceData.brandId,
                                codyPagingSourceData.gender,
                                codyPagingSourceData.category,
                                codyPagingSourceData.personHeightRangeStart,
                                codyPagingSourceData.personHeightRangeEnd,
                                currentPageIndex
                            )
                                .body()!!.data!!.toDomain()
                        }

                        is CodyPagingSourceData.CodyRecommended -> {
                            networkService.findCody(
                                codyPagingSourceData.gender,
                                codyPagingSourceData.category,
                                codyPagingSourceData.personHeightRangeStart,
                                codyPagingSourceData.personHeightRangeEnd,
                                currentPageIndex
                            )
                                .body()!!.data!!.toDomain()
                        }

                        is CodyPagingSourceData.Search -> {
                            networkService.searchCody(
                                codyPagingSourceData.keyword,
                                codyPagingSourceData.genders,
                                codyPagingSourceData.category,
                                codyPagingSourceData.personHeightRangeStart,
                                codyPagingSourceData.personHeightRangeEnd,
                                currentPageIndex
                            )
                                .data!!.toDomain()
                        }

                        CodyPagingSourceData.Bookmark -> {
                            networkService.readBookmarkCody(currentPageIndex)
                                .body()!!.data!!.toDomain()
                        }
                    }
                }

                LoadResult.Page(
                    data = codyItemData.content,
                    prevKey = if (codyItemData.first) null else codyItemData.number - 1,
                    nextKey = if (codyItemData.last) null else codyItemData.number + 1
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }
}

sealed interface CodyPagingSourceData {
    data class Brand(
        val brandId: Long,
        val gender: List<Gender>,
        val category: List<HomeCategory>,
        val personHeightRangeStart: Int? = null,
        val personHeightRangeEnd: Int? = null,
    ) : CodyPagingSourceData

    data class CodyRecommended(
        val gender: List<Gender>,
        val category: List<HomeCategory>,
        val personHeightRangeStart: Int? = null,
        val personHeightRangeEnd: Int? = null,
    ) : CodyPagingSourceData

    data class Search(
        val keyword: String,
        val genders: List<Gender>,
        val category: List<HomeCategory>,
        val personHeightRangeStart: Int? = null,
        val personHeightRangeEnd: Int? = null,
    ) : CodyPagingSourceData

    object Bookmark : CodyPagingSourceData
}