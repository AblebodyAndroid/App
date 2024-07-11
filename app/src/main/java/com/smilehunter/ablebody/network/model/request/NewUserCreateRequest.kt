package com.smilehunter.ablebody.network.model.request

data class NewUserCreateRequest(
    val gender: String,
    val nickname: String,
    val profileImage: Int,
    val verifyingCode: String,
    val agreeRequiredConsent: Boolean,
    val agreeMarketingConsent: Boolean
)
