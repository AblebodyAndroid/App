package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.BuildConfig
import com.smilehunter.ablebody.domain.repository.TokenRepository
import com.smilehunter.ablebody.sharedPreferences.TokenSharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenSharedPreferences: TokenSharedPreferences
) : TokenRepository {

    init {
        if (BuildConfig.DEBUG) {
            tokenSharedPreferences.putAuthToken(BuildConfig.ADMIN_AUTH_TOKEN)
        }
    }

    override val hasToken: Boolean
        get() = tokenSharedPreferences.getAuthToken().let { !it.isNullOrBlank() }

    override val registerOnClearedListener: Flow<Unit>
        get() = tokenSharedPreferences.registerOnClearedListener
            .distinctUntilChanged()
            .onStart { if (!hasToken) emit(Unit) }

    override suspend fun deleteToken() {
        tokenSharedPreferences.clear()
    }
}