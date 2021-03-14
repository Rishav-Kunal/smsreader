package com.rishav.messagetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sms_table")
data class StoredSms (@PrimaryKey val timeInMillis : Long) : Comparable<StoredSms>{
    var message : String? = null
    var originatingNumber : String? = null
    override fun compareTo(other: StoredSms): Int {
        return when{
            this.timeInMillis > other.timeInMillis -> 1
            this.timeInMillis < other.timeInMillis -> -1
            else -> 0
        }
    }
}