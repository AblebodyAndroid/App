package com.smilehunter.ablebody.network.model.response

data class GetAddressResponseData(
    val id: Int,
    val receiverName: String,
    val addressInfo: String,
    val detailAddress: String,
    val zipCode: String,
    val phoneNum: String,
    val deliveryRequest: String
)
