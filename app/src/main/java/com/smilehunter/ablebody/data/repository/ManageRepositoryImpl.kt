package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.domain.repository.ManageRepository
import com.smilehunter.ablebody.network.NetworkService
import com.smilehunter.ablebody.network.model.request.ReportRequest
import javax.inject.Inject

class ManageRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : ManageRepository {

    override suspend fun reportBookmarkPost(id: Long, reason: String, content: String): String {
        return networkService.report(
            ReportRequest(
                contentType = ReportRequest.ContentType.Bookmark,
                id = id,
                reason = reason,
                content = content
            )
        ).data!!
    }

    override suspend fun reportUser(id: Long, reason: String, content: String): String {
        return networkService.report(
            ReportRequest(
                contentType = ReportRequest.ContentType.User,
                id = id,
                reason = reason,
                content = content
            )
        ).data!!
    }

    override suspend fun reportHomeBoard(id: Long, reason: String, content: String): String {
        return networkService.report(
            ReportRequest(
                contentType = ReportRequest.ContentType.HomeBoard,
                id = id,
                reason = reason,
                content = content
            )
        ).data!!
    }
}