package com.smilehunter.ablebody.network

import com.smilehunter.ablebody.domain.model.Gender
import com.smilehunter.ablebody.domain.model.HomeCategory
import com.smilehunter.ablebody.domain.model.ItemGender
import com.smilehunter.ablebody.domain.model.ItemParentCategory
import com.smilehunter.ablebody.domain.model.SortingMethod
import com.smilehunter.ablebody.network.utils.TestRetrofit
import com.smilehunter.ablebody.utils.printResponse
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FindApiUnitTest {
    private val networkAPI: NetworkService = TestRetrofit.getInstance()
    @Test
    fun findItem() {
        val response = runBlocking {
            networkAPI.findItem(
                itemGender = ItemGender.UNISEX,
                parentCategory = ItemParentCategory.ALL,
                childCategory = null,
                sort = SortingMethod.POPULAR
            )
        }
        printResponse(response)
    }

    @Test
    fun findCody() {
        val response = runBlocking {
            networkAPI.findCody(
                genders = listOf(Gender.MALE, Gender.FEMALE),
                category = listOf(HomeCategory.GYMWEAR)
            )
        }
        printResponse(response)
    }
}