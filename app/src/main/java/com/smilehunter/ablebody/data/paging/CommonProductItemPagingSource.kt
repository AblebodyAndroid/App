package com.smilehunter.ablebody.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smilehunter.ablebody.domain.model.ProductItemData
import com.smilehunter.ablebody.network.NetworkService
import javax.inject.Inject

class CommonProductItemPagingSource(
    private val method: suspend (pageIndex: Int) -> ProductItemData
) : PagingSource<Int, ProductItemData.Item>() {

    @Inject
    lateinit var networkService: NetworkService

    override fun getRefreshKey(state: PagingState<Int, ProductItemData.Item>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductItemData.Item> =
        try {
            val currentPageIndex = params.key ?: 0
            val productItemData: ProductItemData = method(currentPageIndex)

            LoadResult.Page(
                data = productItemData.content,
                prevKey = if (productItemData.first) null else productItemData.number - 1,
                nextKey = if (productItemData.last) null else productItemData.number + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}