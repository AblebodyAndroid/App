package com.smilehunter.ablebody.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.smilehunter.ablebody.data.mapper.toDomain
import com.smilehunter.ablebody.data.paging.CommonCodyItemPagingSource
import com.smilehunter.ablebody.data.paging.CommonProductItemPagingSource
import com.smilehunter.ablebody.domain.model.CodyItemData
import com.smilehunter.ablebody.domain.model.ProductItemData
import com.smilehunter.ablebody.domain.repository.BookmarkRepository
import com.smilehunter.ablebody.network.NetworkService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : BookmarkRepository {

    override suspend fun addBookmarkItem(itemID: Long) {
        networkService.addBookmarkItem(itemID)
    }

    override suspend fun readBookmarkItem(): Flow<PagingData<ProductItemData.Item>> {

        return Pager(
            config = PagingConfig(30)
        ) {
            CommonProductItemPagingSource {
                networkService.readBookmarkItem(it).body()!!.data!!.toDomain()
            }
        }
            .flow
    }

    override suspend fun deleteBookmarkItem(itemID: Long) {
        networkService.deleteBookmarkItem(itemID)
    }

    override suspend fun addBookmarkCody(itemID: Long) {
        networkService.addBookmarkCody(itemID)
    }

    override suspend fun readBookmarkCody(): Flow<PagingData<CodyItemData.Item>> {
        return Pager(
            config = PagingConfig(30)
        ) {
            CommonCodyItemPagingSource {
                networkService.readBookmarkCody(it).body()!!.data!!.toDomain()
            }
        }
            .flow
    }

    override suspend fun deleteBookmarkCody(itemID: Long) {
        networkService.deleteBookmarkCody(itemID)
    }
}