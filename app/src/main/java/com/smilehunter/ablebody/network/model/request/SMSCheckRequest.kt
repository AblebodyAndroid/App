package com.smilehunter.ablebody.network.model.request

data class SMSCheckRequest(
    val phoneConfirmId: Long,
    val verifyingCode: String
)
