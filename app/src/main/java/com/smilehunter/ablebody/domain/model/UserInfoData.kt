package com.smilehunter.ablebody.domain.model

data class UserInfoData(
    val createDate: String,
    val modifiedDate: String,
    val gender: Gender,
    val uid: String,
    val phoneNumber: String,
    val nickname: String,
    val name: String,
    val height: Int?,
    val weight: Int?,
    val job: String?,
    val profileUrl: String,
    val introduction: String?,
    val creatorPoint: Int,
    val userType: UserType
) {
    enum class UserType {
        CREATOR, USER
    }
}