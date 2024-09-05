package com.smilehunter.ablebody.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {

    val hasToken: Boolean

    val registerOnClearedListener: Flow<Unit>

    suspend fun deleteToken()
}

