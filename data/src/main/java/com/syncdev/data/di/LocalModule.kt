package com.syncdev.data.di

import android.content.Context
import androidx.room.Room
import com.syncdev.data.local.AppDatabase
import com.syncdev.data.local.ScheduledMedicationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideScheduledMedicationDao(appDatabase: AppDatabase): ScheduledMedicationDao {
        return appDatabase.scheduledMedicationDao()
    }
}