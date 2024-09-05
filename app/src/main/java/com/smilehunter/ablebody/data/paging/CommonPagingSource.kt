package com.smilehunter.ablebody.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

class CommonPagingSource<T : Any>(
    private val method: suspend (pageIndex: Int) -> LoadResult.Page<Int, T>
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> =
        try {
            val currentPageIndex = params.key ?: 0
            method(currentPageIndex)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}