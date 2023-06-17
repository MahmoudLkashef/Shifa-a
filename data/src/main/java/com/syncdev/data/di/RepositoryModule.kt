package com.syncdev.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.syncdev.data.local.ScheduledMedicationDao
import com.syncdev.data.repo.local.LocalRepositoryImp
import com.syncdev.data.repo.remote.RemoteRepositoryImp
import com.syncdev.domain.repo.local.LocalRepository
import com.syncdev.domain.repo.remote.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * A Dagger module for providing instances of the [RemoteRepository] interface.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Provides an instance of the [RemoteRepository] interface with the given dependencies.
     * @param firebaseDatabase An instance of [FirebaseDatabase].
     * @param auth An instance of [FirebaseAuth].
     * @return An instance of [RemoteRepository].
     */
    @Provides
    fun provideRemoteRepository(
        firebaseDatabase: FirebaseDatabase,
        auth: FirebaseAuth
    ): RemoteRepository {
        return RemoteRepositoryImp(firebaseDatabase, auth)
    }

    @Provides
    fun provideLocalRepository(
        scheduledMedicationDao: ScheduledMedicationDao
    ): LocalRepository {
        return LocalRepositoryImp(scheduledMedicationDao)
    }
}
