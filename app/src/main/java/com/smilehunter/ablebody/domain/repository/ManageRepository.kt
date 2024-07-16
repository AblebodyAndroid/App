package com.smilehunter.ablebody.domain.repository

import com.smilehunter.ablebody.network.model.request.ReportRequest
import com.smilehunter.ablebody.network.model.ReportResponse

interface ManageRepository {
    suspend fun report(reportRequest: ReportRequest): ReportResponse
}