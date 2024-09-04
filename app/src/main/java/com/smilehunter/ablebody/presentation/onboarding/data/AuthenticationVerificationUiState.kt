package com.smilehunter.ablebody.presentation.onboarding.data

sealed interface AuthenticationVerificationUiState {

    data class Error(val t: Throwable?) : AuthenticationVerificationUiState

    object Init : AuthenticationVerificationUiState

    object AlreadyUser : AuthenticationVerificationUiState

    object NewUser : AuthenticationVerificationUiState
}