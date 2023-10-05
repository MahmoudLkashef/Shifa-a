package com.syncdev.data.di

import com.syncdev.data.repo.MainRepositoryImp
import com.syncdev.domain.repo.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * A Dagger module for providing instances of the [MainRepository] interface.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideMainRepository(mainRepositoryImp: MainRepositoryImp): MainRepository

}
