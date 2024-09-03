package com.smilehunter.ablebody.data.mapper

import com.smilehunter.ablebody.domain.model.DeliveryTrackingData
import com.smilehunter.ablebody.network.model.response.GetDeliveryInfoResponseData

fun GetDeliveryInfoResponseData.toDomain() =
    DeliveryTrackingData(
        id = id,
        deliveryCompanyName = deliveryCompanyName,
        trackingNumber = trackingNumber
    )