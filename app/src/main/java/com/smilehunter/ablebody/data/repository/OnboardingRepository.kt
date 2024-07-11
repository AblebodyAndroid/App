package com.smilehunter.ablebody.data.repository

import com.smilehunter.ablebody.network.model.CheckSMSResponse
import com.smilehunter.ablebody.network.model.NewUserCreateResponse
import com.smilehunter.ablebody.network.model.SendSMSResponse
import com.smilehunter.ablebody.network.model.StringResponse
import retrofit2.Response

interface OnboardingRepository {
    suspend fun sendSMS(
        phoneNumber: String,
        isNotTestMessage: Boolean = true
    ): Response<SendSMSResponse>
    suspend fun checkSMS(
        phoneConfirmId: Long,
        verifyingCode: String
    ): Response<CheckSMSResponse>
    suspend fun checkNickname(nickname: String): Response<StringResponse>
    suspend fun createNewUser(
        gender: com.smilehunter.ablebody.data.model.Gender,
        nickname: String,
        profileImage: Int,
        verifyingCode: String,
        agreeRequiredConsent: Boolean,
        agreeMarketingConsent: Boolean
    ): Response<NewUserCreateResponse>
}