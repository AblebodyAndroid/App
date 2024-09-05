package com.smilehunter.ablebody.domain.repository

import androidx.paging.PagingData
import com.smilehunter.ablebody.data.model.Gender
import com.smilehunter.ablebody.data.model.HomeCategory
import com.smilehunter.ablebody.domain.model.CodyItemData
import kotlinx.coroutines.flow.Flow

interface FindCodyRepository {

    fun findCody(
        genders: List<Gender>,
        category: List<HomeCategory>,
        personHeightRangeStart: Int? = null,
        personHeightRangeEnd: Int? = null,
    ): Flow<PagingData<CodyItemData.Item>>
}