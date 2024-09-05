package com.smilehunter.ablebody.presentation.my.myInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smilehunter.ablebody.domain.model.UserInfoData
import com.smilehunter.ablebody.domain.usecase.CheckAuthenticationNumberUseCase
import com.smilehunter.ablebody.domain.usecase.CheckDuplicatedNicknameUseCase
import com.smilehunter.ablebody.domain.usecase.EditProfileUseCase
import com.smilehunter.ablebody.domain.usecase.GetUserInfoUseCase
import com.smilehunter.ablebody.domain.usecase.SendAuthenticationNumberUseCase
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import com.smilehunter.ablebody.network.model.request.EditProfile
import com.smilehunter.ablebody.presentation.onboarding.data.CertificationNumberInfoMessageUiState
import com.smilehunter.ablebody.presentation.onboarding.data.NicknameRule
import com.smilehunter.ablebody.presentation.onboarding.utils.CertificationNumberCountDownTimer
import com.smilehunter.ablebody.presentation.onboarding.utils.convertMillisecondsToFormattedTime
import com.smilehunter.ablebody.presentation.onboarding.utils.isNicknameRuleMatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
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
class MyInfoEditViewModel @Inject constructor(
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val editProfileUseCase: EditProfileUseCase,
    private val sendAuthenticationNumberUseCase: SendAuthenticationNumberUseCase,
    private val checkAuthenticationNumberUseCase: CheckAuthenticationNumberUseCase,
    private val checkNicknameUseCase: CheckDuplicatedNicknameUseCase
) : ViewModel() {
    private val _userInfoLiveData = MutableLiveData<UserInfoData>()
    val userLiveData: LiveData<UserInfoData> = _userInfoLiveData
    val phoneNumber = savedStateHandle.getStateFlow<String?>("phoneNumber", null)

    private val _sendErrorLiveData = MutableLiveData<Throwable?>()
    val sendErrorLiveData: LiveData<Throwable?> = _sendErrorLiveData

    fun getMyInfoData() {
        viewModelScope.launch {
            try {
                val userInfo = getUserInfoUseCase.invoke()
                _userInfoLiveData.postValue(userInfo)
                _sendErrorLiveData.postValue(null)

            } catch (e: Exception) {
                e.printStackTrace()
                _sendErrorLiveData.postValue(e)
            }
        }
    }

    private val _nicknameState = MutableStateFlow("")
    val nicknameState = _nicknameState.asStateFlow()
    fun updateNickname(value: String) {
        viewModelScope.launch {
            try {
                _nicknameState.emit(value)
                _sendErrorLiveData.postValue(null)
            } catch (e: Exception) {
                e.printStackTrace()
                _sendErrorLiveData.postValue(e)
            }
        }
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

    fun requestChangeNickname() {
        viewModelScope.launch {
            try {
                val editProfile =
                    EditProfile(nickname = nicknameState.value, null, null, null, null, null, null)
                editProfileUseCase.invoke(editProfile, null)
                _sendErrorLiveData.postValue(null)
            } catch (e: Exception) {
                e.printStackTrace()
                _sendErrorLiveData.postValue(e)
            }
        }
    }

    private val _phoneNumberState = MutableStateFlow("")
    val phoneNumberState = _phoneNumberState.asStateFlow()

    fun updatePhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            try {
                _phoneNumberState.emit(phoneNumber)
                _sendErrorLiveData.postValue(null)
            } catch (e: Exception) {
                e.printStackTrace()
                _sendErrorLiveData.postValue(e)
            }
        }
    }

    private val phoneNumberRegex = "^01[0-1, 7][0-9]{8}\$".toRegex()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val isPhoneNumberCorrectState: StateFlow<Boolean> =
        phoneNumberState.debounce(500L).flatMapLatest { number ->
            flowOf(number.isNotEmpty() && phoneNumberRegex.matches(number))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )


    private val authenticationConfirmId = MutableSharedFlow<Long>()

    private var lastSmsVerificationCodeRequestTime = 0L
    fun requestSmsVerificationCode(phoneNumber: String) {
        viewModelScope.launch(ioDispatcher) {
            try {
                if (System.currentTimeMillis() - lastSmsVerificationCodeRequestTime >= 1000L) {

                    val confirmId = sendAuthenticationNumberUseCase(phoneNumber)
                    authenticationConfirmId.emit(confirmId)
                    lastSmsVerificationCodeRequestTime = System.currentTimeMillis()
                }
                _sendErrorLiveData.postValue(null)
            } catch (e: Exception) {
                e.printStackTrace()
                _sendErrorLiveData.postValue(e)
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
        viewModelScope.launch {
            _certificationNumberState.emit(number)
        }
    }

    private val verificationResultState: StateFlow<Boolean?> =
        combine(certificationNumberState, authenticationConfirmId) { number, id ->
            if (number.length == 4) {
                withContext(Dispatchers.IO) {
                    checkAuthenticationNumberUseCase(id, number)
                }
            } else {
                null
            }
        }
            .catch {
                _sendErrorLiveData.postValue(it)
            }

            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )
    val certificationNumberInfoMessageUiState: StateFlow<CertificationNumberInfoMessageUiState> =
        combine(currentCertificationNumberTime, verificationResultState) { time, result ->
            if (time == 0L) {
                CertificationNumberInfoMessageUiState.Timeout
            } else if (result != null) {
                when {
                    result == true -> {
                        CertificationNumberInfoMessageUiState.Success
                    }

                    else -> {
                        CertificationNumberInfoMessageUiState.InValid
                    }
                }
            } else {
                convertMillisecondsToFormattedTime(time)
                    .run { "${minutes}분 ${seconds}초 남음" }
                    .let { CertificationNumberInfoMessageUiState.Timer(it) }
            }
        }
            .catch {
                it.printStackTrace()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CertificationNumberInfoMessageUiState.Timer("")
            )

}