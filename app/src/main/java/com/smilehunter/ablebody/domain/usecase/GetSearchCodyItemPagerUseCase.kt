package com.smilehunter.ablebody.domain.usecase

import androidx.paging.PagingData
import com.smilehunter.ablebody.domain.model.Gender
import com.smilehunter.ablebody.domain.model.HomeCategory
import com.smilehunter.ablebody.domain.model.CodyItemData
import com.smilehunter.ablebody.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchCodyItemPagerUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    operator fun invoke(
        keyword: String,
        genders: List<Gender>,
        category: List<HomeCategory>,
        personHeightRangeStart: Int? = null,
        personHeightRangeEnd: Int? = null,
    ): Flow<PagingData<CodyItemData.Item>> {
        return searchRepository.searchCody(
            keyword = keyword,
            genders = genders,
            category = category,
            personHeightRangeStart = personHeightRangeStart,
            personHeightRangeEnd = personHeightRangeEnd
        )
    }
}