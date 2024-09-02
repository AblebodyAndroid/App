package com.smilehunter.ablebody.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smilehunter.ablebody.data.mapper.toDomain
import com.smilehunter.ablebody.data.model.ItemChildCategory
import com.smilehunter.ablebody.data.model.ItemGender
import com.smilehunter.ablebody.data.model.ItemParentCategory
import com.smilehunter.ablebody.data.model.SortingMethod
import com.smilehunter.ablebody.domain.model.ProductItemData
import com.smilehunter.ablebody.network.NetworkService
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductItemPagerUseCase @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkService: NetworkService
) {

    operator fun invoke(
        productItemPagingSourceData: ProductItemPagingSourceData
    ): Flow<PagingData<ProductItemData.Item>> =
        Pager(
            config = PagingConfig(pageSize = 10),
            initialKey = 0
        ) {
            ProductItemPagingSource(productItemPagingSourceData)
        }
            .flow


    private inner class ProductItemPagingSource(
        private val productItemPagingSourceData: ProductItemPagingSourceData
    ) : PagingSource<Int, ProductItemData.Item>() {
        override fun getRefreshKey(state: PagingState<Int, ProductItemData.Item>): Int? =
            state.anchorPosition

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductItemData.Item> =
            try {
                val currentPageIndex = params.key ?: 0
                val productItemData: ProductItemData = withContext(ioDispatcher) {
                    when (productItemPagingSourceData) {
                        is ProductItemPagingSourceData.Brand -> {
                            networkService.brandDetailItem(
                                productItemPagingSourceData.sortingMethod,
                                productItemPagingSourceData.brandID,
                                productItemPagingSourceData.itemGender,
                                productItemPagingSourceData.itemParentCategory,
                                productItemPagingSourceData.itemChildCategory,
                                currentPageIndex
                            )
                                .body()!!.data!!.toDomain()
                        }

                        is ProductItemPagingSourceData.Item -> {
                            networkService.findItem(
                                productItemPagingSourceData.sortingMethod,
                                productItemPagingSourceData.itemGender,
                                productItemPagingSourceData.itemParentCategory,
                                productItemPagingSourceData.itemChildCategory,
                                currentPageIndex
                            )
                                .body()!!.data!!.toDomain()
                        }

                        is ProductItemPagingSourceData.Search -> {
                            networkService.searchItem(
                                productItemPagingSourceData.sortingMethod,
                                productItemPagingSourceData.keyword,
                                productItemPagingSourceData.itemGender,
                                productItemPagingSourceData.itemParentCategory,
                                productItemPagingSourceData.itemChildCategory,
                                currentPageIndex
                            )
                                .data!!.toDomain()
                        }

                        ProductItemPagingSourceData.Bookmark -> {
                            networkService.readBookmarkItem(currentPageIndex)
                                .body()!!.data!!.toDomain()
                        }
                    }
                }

                LoadResult.Page(
                    data = productItemData.content,
                    prevKey = if (productItemData.first) null else productItemData.number - 1,
                    nextKey = if (productItemData.last) null else productItemData.number + 1
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
    }
}

sealed interface ProductItemPagingSourceData {
    data class Brand(
        val sortingMethod: SortingMethod,
        val brandID: Long,
        val itemGender: ItemGender,
        val itemParentCategory: ItemParentCategory,
        val itemChildCategory: ItemChildCategory?,
    ) : ProductItemPagingSourceData

    data class Item(
        val sortingMethod: SortingMethod,
        val itemGender: ItemGender,
        val itemParentCategory: ItemParentCategory,
        val itemChildCategory: ItemChildCategory?,
    ) : ProductItemPagingSourceData

    data class Search(
        val sortingMethod: SortingMethod,
        val keyword: String,
        val itemGender: ItemGender,
        val itemParentCategory: ItemParentCategory,
        val itemChildCategory: ItemChildCategory?,
    ) : ProductItemPagingSourceData

    object Bookmark : ProductItemPagingSourceData
}