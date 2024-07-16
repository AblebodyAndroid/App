package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.data.model.Gender
import com.smilehunter.ablebody.data.model.HomeCategory
import com.smilehunter.ablebody.domain.repository.FindCodyRepository
import com.smilehunter.ablebody.network.model.FindCodyResponse
import com.smilehunter.ablebody.network.NetworkService
import retrofit2.Response
import javax.inject.Inject

class FindCodyRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
): FindCodyRepository {
    override suspend fun findCody(
        genders: List<Gender>,
        category: List<HomeCategory>,
        personHeightRangeStart: Int?,
        personHeightRangeEnd: Int?,
        page: Int,
        size: Int
    ): Response<FindCodyResponse> {
        return networkService.findCody(genders, category, personHeightRangeStart, personHeightRangeEnd, page, size)
    }
}