package com.smilehunter.ablebody.domain.repository

import com.smilehunter.ablebody.domain.model.Gender

interface OnboardingRepository {

    /**
     * phoneConfirmId를 반환함
     */

    suspend fun sendSMS(
        phoneNumber: String,
        isNotTestMessage: Boolean = true
    ): Long

    /**
     * 이미 회원가입 완료된 유저들은 true 반환함
     */

    suspend fun checkSMS(
        phoneConfirmId: Long,
        verifyingCode: String
    ): Boolean

    /**
     * 닉네임 사용 가능 여부를 반환함
     */

    suspend fun checkNickname(nickname: String): Boolean

    /**
     * 이미 회원가입 완료된 유저들은 true 반환함
     */

    suspend fun createNewUser(
        gender: Gender,
        nickname: String,
        profileImage: Int,
        verifyingCode: String,
        agreeRequiredConsent: Boolean,
        agreeMarketingConsent: Boolean
    ): Boolean
}