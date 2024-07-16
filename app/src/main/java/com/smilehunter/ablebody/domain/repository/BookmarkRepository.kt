package com.smilehunter.ablebody.domain.repository

import com.smilehunter.ablebody.network.model.AbleBodyResponse
import com.smilehunter.ablebody.network.model.response.ReadBookmarkCodyData
import com.smilehunter.ablebody.network.model.response.ReadBookmarkItemData
import retrofit2.Response

interface BookmarkRepository {
    suspend fun addBookmarkItem(itemID: Long): Response<AbleBodyResponse<String>>

    suspend fun readBookmarkItem(
        page: Int = 0,
        size: Int = 20
    ): Response<AbleBodyResponse<ReadBookmarkItemData>>

    suspend fun deleteBookmarkItem(itemID: Long): Response<AbleBodyResponse<String>>

    suspend fun addBookmarkCody(itemID: Long): Response<AbleBodyResponse<String>>

    suspend fun readBookmarkCody(
        page: Int = 0,
        size: Int = 20
    ): Response<AbleBodyResponse<ReadBookmarkCodyData>>

    suspend fun deleteBookmarkCody(itemID: Long): Response<AbleBodyResponse<String>>
}