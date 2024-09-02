package com.smilehunter.ablebody.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smilehunter.ablebody.domain.model.CodyItemData
import com.smilehunter.ablebody.network.NetworkService
import javax.inject.Inject

class CommonCodyItemPagingSource(
    private val method: suspend (pageIndex: Int) -> CodyItemData
) : PagingSource<Int, CodyItemData.Item>() {

    @Inject
    lateinit var networkService: NetworkService

    override fun getRefreshKey(state: PagingState<Int, CodyItemData.Item>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CodyItemData.Item> =
        try {
            val currentPageIndex = params.key ?: 0
            val codyItemData: CodyItemData = method(currentPageIndex)

            LoadResult.Page(
                data = codyItemData.content,
                prevKey = if (codyItemData.first) null else codyItemData.number - 1,
                nextKey = if (codyItemData.last) null else codyItemData.number + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}