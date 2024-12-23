package com.example.testassessment.di

import android.content.Context
import androidx.room.Room
import com.example.testassessment.roomdb.LocalDb
import com.example.testassessment.roomdb.RoomDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserInfoDao(localDb: LocalDb): RoomDao {
        return localDb.dao
    }

    @Provides
    @Singleton
    fun provideLocalDb(@ApplicationContext context: Context): LocalDb {
        return LocalDb.getDatabase(context)
    }
}