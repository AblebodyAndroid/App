package com.smilehunter.ablebody.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smilehunter.ablebody.data.mapper.toDomain
import com.smilehunter.ablebody.domain.model.UserBoardData
import com.smilehunter.ablebody.network.NetworkService
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UserBoardPagingSource(
    private val uid: String? = null
) : PagingSource<Int, UserBoardData.Content>() {

    @Inject
    lateinit var ioDispatcher: CoroutineDispatcher

    @Inject
    lateinit var networkDataSource: NetworkService

    override fun getRefreshKey(state: PagingState<Int, UserBoardData.Content>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserBoardData.Content> =
        try {
            val currentPageIndex = params.key ?: 0

            val userBoardData = networkDataSource.getMyBoard(
                uid = uid,
                page = currentPageIndex,
                size = params.loadSize
            ).data!!.toDomain()

            LoadResult.Page(
                data = userBoardData.content,
                prevKey = if (userBoardData.first) null else userBoardData.number - 1,
                nextKey = if (userBoardData.last) null else userBoardData.number + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}