package com.syncdev.data.di

import com.syncdev.domain.repo.remote.RemoteRepository
import com.syncdev.domain.usecase.auth.doctor.LoginDoctorUseCase
import com.syncdev.domain.usecase.auth.doctor.RegisterDoctorUseCase
import com.syncdev.domain.usecase.auth.patient.LoginPatientUseCase
import com.syncdev.domain.usecase.auth.patient.RegisterPatientUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * A Dagger module for providing instances of the various use cases in the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    /**
     * Provides an instance of the [RegisterPatientUseCase] with the given [RemoteRepository] dependency.
     * @param remoteRepository An instance of [RemoteRepository].
     * @return An instance of [RegisterPatientUseCase].
     */
    @Provides
    fun provideRegisterPatientUseCase(remoteRepository: RemoteRepository): RegisterPatientUseCase{
        return RegisterPatientUseCase(remoteRepository)
    }

    /**
     * Provides an instance of the [LoginPatientUseCase] with the given [RemoteRepository] dependency.
     * @param remoteRepository An instance of [RemoteRepository].
     * @return An instance of [LoginPatientUseCase].
     */
    @Provides
    fun provideLoginPatientUseCase(remoteRepository: RemoteRepository): LoginPatientUseCase {
        return LoginPatientUseCase(remoteRepository)
    }

    /**
     * Provides an instance of the [RegisterDoctorUseCase] with the given [RemoteRepository] dependency.
     * @param remoteRepository An instance of [RemoteRepository].
     * @return An instance of [RegisterDoctorUseCase].
     */
    @Provides
    fun provideRegisterDoctorUseCase(remoteRepository: RemoteRepository): RegisterDoctorUseCase {
        return RegisterDoctorUseCase(remoteRepository)
    }

    /**
     * Provides an instance of the [LoginDoctorUseCase] with the given [RemoteRepository] dependency.
     * @param remoteRepository An instance of [RemoteRepository].
     * @return An instance of [LoginDoctorUseCase].
     */
    @Provides
    fun provideLoginDoctorUseCase(remoteRepository: RemoteRepository): LoginDoctorUseCase {
        return LoginDoctorUseCase(remoteRepository)
    }
}
