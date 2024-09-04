package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.common.exception.SMSRequestCountLimitException
import com.smilehunter.ablebody.data.model.Gender
import com.smilehunter.ablebody.domain.repository.OnboardingRepository
import com.smilehunter.ablebody.network.NetworkService
import com.smilehunter.ablebody.sharedPreferences.TokenSharedPreferences
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val tokenSharedPreferences: TokenSharedPreferences
) : OnboardingRepository {

    override suspend fun sendSMS(phoneNumber: String, isNotTestMessage: Boolean): Long {
        val response = networkService.sendSMS(phoneNumber, isNotTestMessage).body()!!.data!!

        if (response.requestCnt > 10) {
            throw SMSRequestCountLimitException()
        }

        return response.phoneConfirmId
    }

    override suspend fun checkSMS(phoneConfirmId: Long, verifyingCode: String): Boolean {
        return networkService.checkSMS(phoneConfirmId, verifyingCode)
            .also { response ->
                response.body()?.data?.tokens?.let { token ->
                    tokenSharedPreferences.putAuthToken(token.authToken)
                    tokenSharedPreferences.putRefreshToken(token.refreshToken)
                }
            }
            .body()!!.data!!.registered
    }

    override suspend fun checkNickname(nickname: String): Boolean =
        networkService.checkNickname(nickname).isSuccessful

    override suspend fun createNewUser(
        gender: Gender,
        nickname: String,
        profileImage: Int,
        verifyingCode: String,
        agreeRequiredConsent: Boolean,
        agreeMarketingConsent: Boolean
    ): Boolean {
        return networkService.createNewUser(
            gender = gender,
            nickname = nickname,
            profileImage = profileImage,
            verifyingCode = verifyingCode,
            agreeRequiredConsent = agreeRequiredConsent,
            agreeMarketingConsent = agreeMarketingConsent
        ).also { response ->
            response.body()?.data?.tokens?.let { token ->
                tokenSharedPreferences.putAuthToken(token.authToken)
                tokenSharedPreferences.putRefreshToken(token.refreshToken)
            }
        }
            .body()!!.data!!.registered
    }
}