package com.smilehunter.ablebody.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.smilehunter.ablebody.data.mapper.toDomain
import com.smilehunter.ablebody.data.paging.CommonPagingSource
import com.smilehunter.ablebody.domain.model.NotificationItemData
import com.smilehunter.ablebody.domain.repository.NotificationRepository
import com.smilehunter.ablebody.network.NetworkService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : NotificationRepository {

    override fun getMyNoti(): Flow<PagingData<NotificationItemData.Content>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            initialKey = 0,
            pagingSourceFactory = {
                CommonPagingSource { pageIndex ->
                    val item = networkService.getMyNoti(page = pageIndex).data!!.toDomain()

                    PagingSource.LoadResult.Page(
                        data = item.content,
                        prevKey = if (item.first) null else item.number - 1,
                        nextKey = if (item.last) null else item.number + 1
                    )
                }
            }
        )
            .flow
    }

    override suspend fun checkMyNoti(id: Long): String =
        networkService.checkMyNoti(id).data!!

    override suspend fun checkAllMyNoti(): String =
        networkService.checkMyNoti(-1).data!!
}