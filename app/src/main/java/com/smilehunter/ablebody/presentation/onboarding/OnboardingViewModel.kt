package com.smilehunter.ablebody.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smilehunter.ablebody.domain.model.Gender
import com.smilehunter.ablebody.domain.usecase.CheckAuthenticationNumberUseCase
import com.smilehunter.ablebody.domain.usecase.CheckDuplicatedNicknameUseCase
import com.smilehunter.ablebody.domain.usecase.RegisterAppUseCase
import com.smilehunter.ablebody.domain.usecase.SendAuthenticationNumberUseCase
import com.smilehunter.ablebody.domain.usecase.SyncAppStatusUseCase
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import com.smilehunter.ablebody.presentation.onboarding.data.AuthenticationVerificationUiState
import com.smilehunter.ablebody.presentation.onboarding.data.CertificationNumberInfoMessageUiState
import com.smilehunter.ablebody.presentation.onboarding.data.NicknameRule
import com.smilehunter.ablebody.presentation.onboarding.data.ProfileImages
import com.smilehunter.ablebody.presentation.onboarding.data.TermsAgreements
import com.smilehunter.ablebody.presentation.onboarding.utils.CertificationNumberCountDownTimer
import com.smilehunter.ablebody.presentation.onboarding.utils.isNicknameRuleMatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val sendAuthenticationNumberUseCase: SendAuthenticationNumberUseCase,
    private val checkAuthenticationNumberUseCase: CheckAuthenticationNumberUseCase,
    private val checkNicknameUseCase: CheckDuplicatedNicknameUseCase,
    private val registerAppUseCase: RegisterAppUseCase,
    private val syncAppStatusUseCase: SyncAppStatusUseCase
) : ViewModel() {

    val phoneNumberState: StateFlow<String> get() = _phoneNumberState.asStateFlow()
    private val _phoneNumberState = MutableStateFlow<String>("")

    fun updatePhoneNumber(phoneNumber: String) {
        viewModelScope.launch { _phoneNumberState.emit(phoneNumber) }
    }

    private val phoneNumberRegex = "^01[0-1, 7][0-9]{8}\$".toRegex()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val isPhoneNumberCorrectState: StateFlow<Boolean> =
        phoneNumberState.debounce(500L)
            .flatMapLatest { number ->
                flowOf(number.isNotEmpty() && phoneNumberRegex.matches(number))
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val phoneNumberMessageStateUi: StateFlow<String> =
        phoneNumberState.debounce(500L)
            .flatMapLatest { number ->
                if (number.isEmpty() || phoneNumberRegex.matches(number)) {
                    flowOf("")
                } else {
                    flowOf("휴대폰 번호 양식에 맞지 않아요.")
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ""
            )


    private val certificationId = MutableSharedFlow<Long>()

    private var lastSmsVerificationCodeRequestTime = 0L
    fun requestSmsVerificationCode(phoneNumber: String) {
        viewModelScope.launch(ioDispatcher) {
            if (System.currentTimeMillis() - lastSmsVerificationCodeRequestTime >= 1000L) {
                val id = sendAuthenticationNumberUseCase(phoneNumber)
                certificationId.emit(id)
                lastSmsVerificationCodeRequestTime = System.currentTimeMillis()
            }
        }
    }

    private val _currentCertificationNumberTime = MutableStateFlow(180000L)
    private val currentCertificationNumberTime = _currentCertificationNumberTime.asStateFlow()

    private val certificationNumberCountDownTimer = CertificationNumberCountDownTimer()

    fun startCertificationNumberTimer() {
        certificationNumberCountDownTimer.startCertificationNumberTimer()
        certificationNumberCountDownTimer.registerOnChangeListener(
            object : CertificationNumberCountDownTimer.Callback {
                override fun onChangeValue(value: Long) {
                    _currentCertificationNumberTime.value = value
                }
            }
        )
    }

    fun cancelCertificationNumberCountDownTimer() {
        certificationNumberCountDownTimer.unRegisterOnChangeListener()
        certificationNumberCountDownTimer.cancelCertificationNumberCountDownTimer()
    }

    private val _certificationNumberState = MutableStateFlow<String>("")
    val certificationNumberState: StateFlow<String> = _certificationNumberState.asStateFlow()

    fun updateCertificationNumber(number: String) {
        viewModelScope.launch { _certificationNumberState.emit(number) }
    }

    val verificationResultState: StateFlow<AuthenticationVerificationUiState> =
        combine(certificationNumberState, certificationId) { number, id ->
            require(number.length == 4)
            val isRegisteredUser = checkAuthenticationNumberUseCase(id, number)

            if (isRegisteredUser) {
                return@combine AuthenticationVerificationUiState.AlreadyUser
            }
            AuthenticationVerificationUiState.NewUser
        }
            .catch {
                AuthenticationVerificationUiState.Error(it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AuthenticationVerificationUiState.Init
            )

    val certificationNumberInfoMessageUiState =
        combine(currentCertificationNumberTime, verificationResultState) { time, result ->
            if (time == 0L) {
                CertificationNumberInfoMessageUiState.Timeout
            } else run {
                when (result) {
                    is AuthenticationVerificationUiState.AlreadyUser -> {
                        CertificationNumberInfoMessageUiState.Already
                    }

                    is AuthenticationVerificationUiState.NewUser -> {
                        CertificationNumberInfoMessageUiState.Success
                    }

                    else -> {
                        CertificationNumberInfoMessageUiState.InValid
                    }
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CertificationNumberInfoMessageUiState.Timer("")
        )

    private val _nicknameState = MutableStateFlow("")
    val nicknameState = _nicknameState.asStateFlow()

    fun updateNickname(value: String) {
        _nicknameState.value = value
    }

    private val regex1 = "[0-9a-z_.]{1,20}".toRegex()
    private val startsWithDotRegex = "^[.].*\$".toRegex()
    private val onlyNumberRegex = "^[0-9]*\$".toRegex()
    private val regex7 = "^[._]*\$".toRegex()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val nicknameRuleState: StateFlow<NicknameRule> =
        nicknameState.debounce(100L).flatMapLatest { nickname ->
            if (nickname.isEmpty()) {
                flowOf(NicknameRule.Nothing)
            } else if (!isNicknameRuleMatch(nickname, regex1)) {
                flowOf(NicknameRule.UnAvailable)
            } else if (isNicknameRuleMatch(nickname, startsWithDotRegex)) {
                flowOf(NicknameRule.StartsWithDot)
            } else if (isNicknameRuleMatch(nickname, onlyNumberRegex)) {
                flowOf(NicknameRule.OnlyNumber)
            } else if (isNicknameRuleMatch(nickname, regex7)) {
                flowOf(NicknameRule.UnAvailable)
            } else {
                withContext(ioDispatcher) {
                    if (checkNicknameUseCase(nickname)) {
                        flowOf(NicknameRule.Available)
                    } else {
                        flowOf(NicknameRule.InUsed)
                    }
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NicknameRule.Nothing
        )

    private val _genderState = MutableStateFlow<Gender?>(null)
    val genderState = _genderState.asStateFlow()

    fun updateGender(value: Gender) {
        _genderState.value = value
    }

    private val _profileImageState = MutableStateFlow<ProfileImages?>(null)
    val profileImageState = _profileImageState.asStateFlow()

    fun updateProfileImage(value: ProfileImages) {
        _profileImageState.value = value
    }

    private val _termsAgreementsListState = MutableStateFlow<List<TermsAgreements>>(emptyList())
    val termsAgreementsListState = _termsAgreementsListState.asStateFlow()

    fun updateTermsAgreementsListState(value: List<TermsAgreements>) {
        viewModelScope.launch { _termsAgreementsListState.emit(value) }
    }

    private val _createNewUser = MutableSharedFlow<Unit>()
    val createNewUser: SharedFlow<Unit> = _createNewUser

    fun createNewUser(
        gender: Gender,
        nickname: String,
        profileImage: Int,
        verifyingCode: String,
        agreeRequiredConsent: Boolean,
        agreeMarketingConsent: Boolean
    ) {
        viewModelScope.launch(ioDispatcher) {
            registerAppUseCase(
                gender = gender,
                nickname = nickname,
                profileImage = profileImage,
                verifyingCode = verifyingCode,
                agreeRequiredConsent = agreeRequiredConsent,
                agreeMarketingConsent = agreeMarketingConsent
            ).also { _createNewUser.emit(Unit) }
        }
    }

    fun updateFCMTokenAndAppVersion() {
        viewModelScope.launch(ioDispatcher) {
            try {
                syncAppStatusUseCase()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}