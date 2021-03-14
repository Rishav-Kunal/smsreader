package com.rishav.messagetracker.repository

import androidx.lifecycle.LiveData
import com.rishav.messagetracker.data.StoredSms

interface SmsRepository {

    suspend fun saveSms(sms: StoredSms)

    suspend fun saveAllSms(smsList: List<StoredSms>)

    suspend fun updateSms(sms: StoredSms)

    suspend fun deleteSms(sms: StoredSms)

    fun getAllSms(): LiveData<List<StoredSms>?>
}