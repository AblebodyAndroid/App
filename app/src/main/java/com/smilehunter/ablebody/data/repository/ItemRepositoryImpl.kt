package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.domain.repository.ItemRepository
import com.smilehunter.ablebody.network.model.ItemDetailResponse
import com.smilehunter.ablebody.network.NetworkService
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
): ItemRepository {

    override suspend fun itemDetail(id: Long): ItemDetailResponse {
        return networkService.itemDetail(id)
    }

}