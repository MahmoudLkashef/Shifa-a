package com.syncdev.data.di

import android.content.Context
import androidx.room.Room
import com.syncdev.data.local.AppDatabase
import com.syncdev.data.local.ScheduledMedicationDao
import com.syncdev.data.local.data_source.LocalDataSourceImp
import com.syncdev.domain.local.LocalDataSource
import dagger.Binds
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

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalBindsModule {
    @Binds
    abstract fun provideLocalDataSource(localDataSourceImp: LocalDataSourceImp): LocalDataSource
}