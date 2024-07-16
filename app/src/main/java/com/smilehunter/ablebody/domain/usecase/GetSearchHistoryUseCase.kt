package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.data.repository.SearchRepository
import com.smilehunter.ablebody.domain.model.SearchHistoryQuery
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetSearchHistoryUseCase @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val searchRepository: SearchRepository
) {

    operator fun invoke(): Flow<List<SearchHistoryQuery>> {
        return searchRepository.getSearchHistoryQueries().flowOn(ioDispatcher)
    }
}