package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.OrderManagementRepository
import com.smilehunter.ablebody.domain.model.DeliveryTrackingData
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import com.smilehunter.ablebody.network.model.response.GetDeliveryInfoResponseData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDeliveryTrackingNumberUseCase @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val orderManagementRepository: OrderManagementRepository
) {

    suspend operator fun invoke(id: String): DeliveryTrackingData = withContext(ioDispatcher) {
        orderManagementRepository.getDeliveryTrackingNumber(id).data!!.toDomain()
    }
}

private fun GetDeliveryInfoResponseData.toDomain() =
    DeliveryTrackingData(
        id = id,
        deliveryCompanyName = deliveryCompanyName,
        trackingNumber = trackingNumber
    )