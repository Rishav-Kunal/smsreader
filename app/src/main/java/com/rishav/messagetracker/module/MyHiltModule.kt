package com.rishav.messagetracker.module

import android.content.Context
import androidx.room.Room
import com.rishav.messagetracker.database.SmsDatabase
import com.rishav.messagetracker.repository.SmsRepository
import com.rishav.messagetracker.repository.SmsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyHiltModule {
    @Singleton
    @Provides
    fun provideSmsDatabase(@ApplicationContext applicationContext: Context): SmsDatabase {
        return Room.databaseBuilder(
            applicationContext,
            SmsDatabase::class.java,
            "sms_table"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideSmsDao(smsDatabase: SmsDatabase) = smsDatabase.smsDao

    @Provides
    fun provideSmsRepository(smsRepositoryImpl: SmsRepositoryImpl) : SmsRepository{
        return smsRepositoryImpl
    }
}