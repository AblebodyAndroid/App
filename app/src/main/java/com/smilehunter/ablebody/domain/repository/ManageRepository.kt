package com.smilehunter.ablebody.domain.repository

interface ManageRepository {

    suspend fun reportBookmarkPost(
        id: Long,
        reason: String,
        content: String
    ): String

    suspend fun reportUser(
        id: Long,
        reason: String,
        content: String
    ): String

    suspend fun reportHomeBoard(
        id: Long,
        reason: String,
        content: String
    ): String
}