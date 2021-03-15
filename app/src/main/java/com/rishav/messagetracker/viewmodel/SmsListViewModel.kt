package com.rishav.messagetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rishav.messagetracker.repository.SmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmsListViewModel @Inject constructor(private val smsRepository: SmsRepository) : ViewModel() {
    val smsList = smsRepository.getAllSms()
    val timedSmsList : LiveData<List<Any>> get() = _timedSmsList
    private var _timedSmsList : MutableLiveData<List<Any>> = MutableLiveData()

    fun getTimedSmsList() = viewModelScope.launch{
        smsRepository.getAllSmsInInterval()?.let {
          _timedSmsList.postValue(it)
        }
    }
}