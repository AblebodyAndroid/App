package com.smilehunter.ablebody.domain.model


enum class SortingMethod(val string: String) {
    POPULAR(string = "인기순"),
    RECENT(string = "최신순"),
    ALPHABET(string = "이름순")
}