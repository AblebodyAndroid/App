package com.smilehunter.ablebody.presentation.comment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smilehunter.ablebody.data.result.Result
import com.smilehunter.ablebody.data.result.asResult
import com.smilehunter.ablebody.domain.usecase.AddCreatorDetailCommentUseCase
import com.smilehunter.ablebody.domain.usecase.AddCreatorDetailReplyUseCase
import com.smilehunter.ablebody.domain.usecase.DeleteCreatorDetailCommentUseCase
import com.smilehunter.ablebody.domain.usecase.DeleteCreatorDetailReplyUseCase
import com.smilehunter.ablebody.domain.usecase.GetCommentListUseCase
import com.smilehunter.ablebody.domain.usecase.LikeCreatorDetailCommentUseCase
import com.smilehunter.ablebody.domain.usecase.LikeCreatorDetailReplyUseCase
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import com.smilehunter.ablebody.presentation.comment.data.CommentUiState
import com.smilehunter.ablebody.presentation.main.LocalUserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
    getCommentListUseCase: GetCommentListUseCase,
    private val addCreatorDetailCommentUseCase: AddCreatorDetailCommentUseCase,
    private val addCreatorDetailReplyUseCase: AddCreatorDetailReplyUseCase,
    private val deleteCreatorDetailCommentUseCase: DeleteCreatorDetailCommentUseCase,
    private val deleteCreatorDetailReplyUseCase: DeleteCreatorDetailReplyUseCase,
    private val likeCreatorDetailCommentUseCase: LikeCreatorDetailCommentUseCase,
    private val likeCreatorDetailReplyUseCase: LikeCreatorDetailReplyUseCase
): ViewModel() {

    private val _networkRefreshFlow = MutableSharedFlow<Unit>()
    private val networkRefreshFlow = _networkRefreshFlow.asSharedFlow()

    fun refreshNetwork() {
        viewModelScope.launch { _networkRefreshFlow.emit(Unit) }
    }

    private val contentID = savedStateHandle.getStateFlow("content_id", -1L)

    private val renewData = MutableSharedFlow<Unit>()

    @OptIn(ExperimentalCoroutinesApi::class)
    val commentListData: StateFlow<CommentUiState> =
        networkRefreshFlow.onSubscription { emit(Unit) }
            .flatMapLatest {
                val localUserProfile = LocalUserProfile.getInstance()
                combine(contentID, renewData.onSubscription { emit(Unit) }) { id, _ ->
                    getCommentListUseCase(id, localUserProfile.uid)
                }
                    .flowOn(ioDispatcher)
                    .asResult()
                    .map {
                        when (it) {
                            is Result.Error -> CommentUiState.LoadFail(it.exception)
                            is Result.Loading -> CommentUiState.Loading
                            is Result.Success -> CommentUiState.CommentList(it.data)
                        }
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CommentUiState.Loading
            )

    fun updateCommentText(text: String) {
        viewModelScope.launch {
            try {
                addCreatorDetailCommentUseCase(contentID.value, text)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            renewData.emit(Unit)
        }
    }

    fun updateReplyText(id: Long, text: String) {
        viewModelScope.launch {
            try {
                addCreatorDetailReplyUseCase(id, text)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            renewData.emit(Unit)
        }
    }

    fun deleteComment(id: Long) {
        viewModelScope.launch {
            try {
                deleteCreatorDetailCommentUseCase(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            renewData.emit(Unit)
        }
    }

    fun deleteReply(id: Long) {
        viewModelScope.launch {
            try {
                deleteCreatorDetailReplyUseCase(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            renewData.emit(Unit)
        }
    }

    fun toggleLikeComment(id: Long) {
        viewModelScope.launch {
            try {
                likeCreatorDetailCommentUseCase(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleLikeReply(id: Long) {
        viewModelScope.launch {
            try {
                likeCreatorDetailReplyUseCase(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}