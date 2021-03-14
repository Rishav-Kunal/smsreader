package com.rishav.messagetracker.repository

import androidx.lifecycle.LiveData
import com.rishav.messagetracker.data.StoredSms
import com.rishav.messagetracker.database.SmsDao
import javax.inject.Inject

class SmsRepositoryImpl @Inject constructor(private val smsDao: SmsDao) : SmsRepository{
    override suspend fun saveSms(sms: StoredSms) {
        smsDao.insert(sms)
    }

    override suspend fun saveAllSms(smsList: List<StoredSms>) {
        smsDao.insertAll(smsList)
    }

    override suspend fun updateSms(sms: StoredSms) {
        smsDao.update(sms)
    }

    override suspend fun deleteSms(sms: StoredSms) {
        smsDao.delete(sms)
    }

    override fun getAllSms(): LiveData<List<StoredSms>?> {
        return smsDao.getAllSms()
    }
}