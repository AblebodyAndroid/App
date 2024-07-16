package com.smilehunter.ablebody.network.model.response

data class GetDeliveryInfoResponseData(
    val id: String,
    val deliveryCompanyName: String,
    val trackingNumber: String
)
