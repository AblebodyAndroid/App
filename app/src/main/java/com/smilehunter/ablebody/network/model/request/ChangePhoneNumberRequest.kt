package com.smilehunter.ablebody.network.model.request

data class ChangePhoneNumberRequest(
    val phoneConfirmId: Long,
    val verifyingCode: String
)
