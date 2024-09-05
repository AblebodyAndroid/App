package com.smilehunter.ablebody.data.mapper

import com.smilehunter.ablebody.Gender
import com.smilehunter.ablebody.UserInfoPreferences
import com.smilehunter.ablebody.domain.model.LocalUserInfoData
import com.smilehunter.ablebody.domain.model.UserInfoData
import com.smilehunter.ablebody.network.model.response.UserDataResponseData

fun UserDataResponseData.toDomain(): UserInfoData {
    val isCreator = authorities.any { it.authorityName == "ROLE_CRT" }

    return UserInfoData(
        createDate = createDate,
        modifiedDate = modifiedDate,
        gender = gender,
        uid = uid,
        phoneNumber = phone,
        nickname = nickname,
        name = name,
        height = height,
        weight = weight,
        job = job,
        profileUrl = profileUrl,
        introduction = introduction,
        creatorPoint = creatorPoint,
        userType = if (isCreator) UserInfoData.UserType.CREATOR else UserInfoData.UserType.USER
    )
}

fun UserInfoPreferences.asExternalModel() =
    LocalUserInfoData(
        uid = uid,
        name = name,
        nickname = nickname,
        gender = when (gender) {
            Gender.MALE -> LocalUserInfoData.Gender.MALE
            Gender.FEMALE -> LocalUserInfoData.Gender.FEMALE
            else -> LocalUserInfoData.Gender.UNRECOGNIZED
        },
        profileImageUrl = profileImageUrl
    )