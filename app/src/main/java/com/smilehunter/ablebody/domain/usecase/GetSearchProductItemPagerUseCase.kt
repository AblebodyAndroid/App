package com.smilehunter.ablebody.domain.usecase

import androidx.paging.PagingData
import com.smilehunter.ablebody.domain.model.ItemChildCategory
import com.smilehunter.ablebody.domain.model.ItemGender
import com.smilehunter.ablebody.domain.model.ItemParentCategory
import com.smilehunter.ablebody.domain.model.SortingMethod
import com.smilehunter.ablebody.domain.model.ProductItemData
import com.smilehunter.ablebody.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchProductItemPagerUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    operator fun invoke(
        sortingMethod: SortingMethod,
        keyword: String,
        itemGender: ItemGender,
        itemParentCategory: ItemParentCategory,
        itemChildCategory: ItemChildCategory?,
    ): Flow<PagingData<ProductItemData.Item>> {
        return searchRepository.searchItem(
            sortingMethod = sortingMethod,
            keyword = keyword,
            itemGender = itemGender,
            itemParentCategory = itemParentCategory,
            itemChildCategory = itemChildCategory
        )
    }
}