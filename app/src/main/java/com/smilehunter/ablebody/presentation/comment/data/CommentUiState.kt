package com.smilehunter.ablebody.presentation.comment.data

import com.smilehunter.ablebody.domain.model.CommentListData

sealed interface CommentUiState {

    data class LoadFail(val t: Throwable?): CommentUiState

    object Loading: CommentUiState

    data class CommentList(val data: List<CommentListData>): CommentUiState
}