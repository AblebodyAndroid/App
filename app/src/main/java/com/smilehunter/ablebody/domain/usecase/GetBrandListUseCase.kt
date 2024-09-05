package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.data.model.SortingMethod
import com.smilehunter.ablebody.domain.model.BrandListData
import com.smilehunter.ablebody.domain.repository.BrandRepository
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBrandListUseCase @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val brandRepository: BrandRepository
) {

    suspend operator fun invoke(
        sortingMethod: SortingMethod
    ): List<BrandListData> = withContext(ioDispatcher) {
        brandRepository.brandMain(sortingMethod)
    }

}