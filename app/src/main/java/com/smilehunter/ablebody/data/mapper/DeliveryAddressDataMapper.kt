package com.smilehunter.ablebody.data.mapper

import com.smilehunter.ablebody.domain.model.DeliveryAddressData
import com.smilehunter.ablebody.network.model.response.GetAddressResponseData

fun GetAddressResponseData.toDomain() =
    DeliveryAddressData(
        id = id,
        userName = receiverName,
        roadAddress = addressInfo,
        roadDetailAddress = detailAddress,
        zipCode = zipCode,
        phoneNumber = phoneNum,
        deliveryRequestMessage = deliveryRequest
    )