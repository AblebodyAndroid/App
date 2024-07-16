package com.smilehunter.ablebody.domain.repository

import com.smilehunter.ablebody.network.model.ItemDetailResponse

interface ItemRepository {
    suspend fun itemDetail(id: Long): ItemDetailResponse
}