package com.smilehunter.ablebody.network.model.response


data class UniSearchResponseData(
    val recommendKeyword: RecommendKeyword
) {
    data class RecommendKeyword(
        val creator: List<String>,
        val qna: List<String>,
        val user: List<String>
    )
}
