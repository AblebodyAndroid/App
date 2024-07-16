package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.domain.repository.ManageRepository
import com.smilehunter.ablebody.network.model.request.ReportRequest
import com.smilehunter.ablebody.network.model.ReportResponse
import com.smilehunter.ablebody.network.NetworkService
import javax.inject.Inject

class ManageRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
): ManageRepository {

    override suspend fun report(reportRequest: ReportRequest): ReportResponse {
        return networkService.report(reportRequest)
    }
}