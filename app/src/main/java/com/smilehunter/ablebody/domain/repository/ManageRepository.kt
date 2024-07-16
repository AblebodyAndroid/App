package com.smilehunter.ablebody.domain.repository

import com.smilehunter.ablebody.network.model.ReportResponse
import com.smilehunter.ablebody.network.model.request.ReportRequest

interface ManageRepository {
    suspend fun report(reportRequest: ReportRequest): ReportResponse
}