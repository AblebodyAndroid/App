package com.smilehunter.ablebody.presentation.my.report

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smilehunter.ablebody.network.model.request.ReportRequest
import com.smilehunter.ablebody.data.repository.ManageRepository
import com.smilehunter.ablebody.network.di.AbleBodyDispatcher
import com.smilehunter.ablebody.network.di.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val manageRepository: ManageRepository,
    @Dispatcher(AbleBodyDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _sendErrorLiveData = MutableLiveData<Throwable?>()
    val sendErrorLiveData: LiveData<Throwable?> = _sendErrorLiveData

    fun reportUser(reportRequest: ReportRequest) {
        Log.d("뷰모델에 신고 들어옴", reportRequest.reason)
        viewModelScope.launch(ioDispatcher) {
            try{
                manageRepository.report(reportRequest)
                _sendErrorLiveData.postValue(null)
            } catch (e: Exception) {
                e.printStackTrace()
                _sendErrorLiveData.postValue(e)
            }
        }
    }
}