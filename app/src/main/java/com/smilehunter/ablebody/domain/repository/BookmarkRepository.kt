package com.smilehunter.ablebody.domain.repository

import androidx.paging.PagingData
import com.smilehunter.ablebody.domain.model.CodyItemData
import com.smilehunter.ablebody.domain.model.ProductItemData
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    suspend fun addBookmarkItem(itemID: Long)

    suspend fun readBookmarkItem(): Flow<PagingData<ProductItemData.Item>>

    suspend fun deleteBookmarkItem(itemID: Long)

    suspend fun addBookmarkCody(itemID: Long)

    suspend fun readBookmarkCody(): Flow<PagingData<CodyItemData.Item>>

    suspend fun deleteBookmarkCody(itemID: Long)
}