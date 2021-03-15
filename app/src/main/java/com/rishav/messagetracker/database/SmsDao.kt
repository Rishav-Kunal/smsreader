package com.rishav.messagetracker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rishav.messagetracker.data.StoredSms

@Dao
interface SmsDao {
    @Insert
    suspend fun insert(sms: StoredSms)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(smsList: List<StoredSms>)

    @Update
    suspend fun update(sms: StoredSms)

    @Delete
    suspend fun delete(sms: StoredSms)

    @Query("SELECT * FROM sms_table ORDER BY timeInMillis DESC")
    fun getAllSms(): LiveData<List<StoredSms>?>

    @Query("SELECT * FROM sms_table WHERE (timeInMillis<= :endTime AND timeInMillis> :startTime) ORDER BY timeInMillis DESC")
    suspend fun getAllSmsInTimeInterval(startTime : Long, endTime : Long): List<StoredSms>?

    @Query("SELECT * FROM sms_table WHERE timeInMillis<= :endTime ORDER BY timeInMillis DESC")
    suspend fun getAllSmsFromYesterday(endTime : Long): List<StoredSms>?
}