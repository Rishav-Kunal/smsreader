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

    override suspend fun getAllSmsInInterval(): List<Any>? {
        val currentTime = System.currentTimeMillis()
        val oneHour = 1 * 1000 * 60 * 60
        val twoHour = 2 * 1000 * 60 * 60
        val threeHour = 3 * 1000 * 60 * 60
        val sixHour = 6 * 1000 * 60 * 60
        val twelveHour = 12 * 1000 * 60 * 60
        val twentyFourHour = 24 * 1000 * 60 * 60
        val list : MutableList<Any> = ArrayList()

        val zeroHourList = smsDao.getAllSmsInTimeInterval(currentTime - oneHour, currentTime)
        if(!zeroHourList.isNullOrEmpty()) {
            list.add("0 hours ago")
            list.addAll(zeroHourList)
        }
        val oneHourList = smsDao.getAllSmsInTimeInterval(currentTime - twoHour, currentTime - oneHour)
        if(!oneHourList.isNullOrEmpty()) {
            list.add("1 hour ago")
            list.addAll(oneHourList)
        }
        val twoHourList = smsDao.getAllSmsInTimeInterval(currentTime - threeHour, currentTime - twoHour)
        if(!twoHourList.isNullOrEmpty()) {
            list.add("2 hours ago")
            list.addAll(twoHourList)
        }
        val threeHourList = smsDao.getAllSmsInTimeInterval(currentTime - sixHour, currentTime - threeHour)
        if(!threeHourList.isNullOrEmpty()) {
            list.add("3 hours ago")
            list.addAll(threeHourList)
        }
        val sixHourList = smsDao.getAllSmsInTimeInterval(currentTime - twelveHour, currentTime - sixHour)
        if(!sixHourList.isNullOrEmpty()) {
            list.add("6 hours ago")
            list.addAll(sixHourList)
        }
        val twelveHourList = smsDao.getAllSmsInTimeInterval(currentTime - twentyFourHour, currentTime - twelveHour)
        if(!twelveHourList.isNullOrEmpty()) {
            list.add("12 hours ago")
            list.addAll(twelveHourList)
        }
        val twentyFourHourList = smsDao.getAllSmsFromYesterday(currentTime - twentyFourHour)
        if(!twentyFourHourList.isNullOrEmpty()) {
            list.add("24 hours ago")
            list.addAll(twentyFourHourList)
        }
        return list

    }
}