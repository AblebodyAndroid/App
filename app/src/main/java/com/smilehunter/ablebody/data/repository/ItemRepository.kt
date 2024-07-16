package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.network.model.ItemDetailResponse

interface ItemRepository {
    suspend fun itemDetail(id: Long): ItemDetailResponse
}