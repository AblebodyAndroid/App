package com.smilehunter.ablebody.network.model.request

data class AddressRequest(
    val receiverName: String,
    val phoneNum: String,
    val addressInfo: String,
    val detailAddress: String,
    val zipCode: String,
    val deliveryRequest: String
)