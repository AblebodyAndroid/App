package com.smilehunter.ablebody.domain.repository

import com.smilehunter.ablebody.data.model.Gender
import com.smilehunter.ablebody.data.model.HomeCategory
import com.smilehunter.ablebody.network.model.FindCodyResponse
import retrofit2.Response

interface FindCodyRepository {

    suspend fun findCody(
        genders: List<Gender>,
        category: List<HomeCategory>,
        personHeightRangeStart: Int? = null,
        personHeightRangeEnd: Int? = null,
        page: Int = 0,
        size: Int = 20
    ): Response<FindCodyResponse>
}