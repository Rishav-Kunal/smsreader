package com.rishav.messagetracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rishav.messagetracker.data.StoredSms

@Database(entities = [StoredSms::class], version = 1, exportSchema = false)
abstract class SmsDatabase : RoomDatabase() {
    abstract val smsDao : SmsDao
}