package com.rishav.messagetracker.viewmodel

import androidx.lifecycle.ViewModel
import com.rishav.messagetracker.repository.SmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SmsListViewModel @Inject constructor(smsRepository: SmsRepository) : ViewModel() {
    val smsList = smsRepository.getAllSms()
}