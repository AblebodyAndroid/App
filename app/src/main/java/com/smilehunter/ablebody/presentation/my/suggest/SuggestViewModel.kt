package com.smilehunter.ablebody.presentation.my.suggest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smilehunter.ablebody.domain.repository.UserRepository
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuggestViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _sendErrorLiveData = MutableLiveData<Throwable?>()
    val sendErrorLiveData: LiveData<Throwable?> = _sendErrorLiveData

    fun sendSuggest(value: String) {
        viewModelScope.launch(ioDispatcher) {
            try {
                userRepository.suggestApp(value)
                _sendErrorLiveData.postValue(null)
            } catch (e: Exception) {
                e.printStackTrace()
                _sendErrorLiveData.postValue(e)
            }
        }
    }
}