package com.smilehunter.ablebody.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.smilehunter.ablebody.data.mapper.toDomain
import com.smilehunter.ablebody.data.model.Gender
import com.smilehunter.ablebody.data.model.HomeCategory
import com.smilehunter.ablebody.data.paging.CommonCodyItemPagingSource
import com.smilehunter.ablebody.domain.model.CodyItemData
import com.smilehunter.ablebody.domain.repository.FindCodyRepository
import com.smilehunter.ablebody.network.NetworkService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindCodyRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : FindCodyRepository {

    override fun findCody(
        genders: List<Gender>,
        category: List<HomeCategory>,
        personHeightRangeStart: Int?,
        personHeightRangeEnd: Int?
    ): Flow<PagingData<CodyItemData.Item>> {

        return Pager(
            config = PagingConfig(pageSize = 10),
            initialKey = 0
        ) {
            CommonCodyItemPagingSource { pageIndex ->
                networkService.findCody(
                    genders = genders,
                    category = category,
                    personHeightRangeStart = personHeightRangeStart,
                    personHeightRangeEnd = personHeightRangeEnd,
                    page = pageIndex
                )
                    .body()!!.data!!.toDomain()
            }
        }
            .flow
    }
}