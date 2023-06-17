package com.syncdev.data.di

import com.syncdev.domain.repo.remote.RemoteRepository
import com.syncdev.domain.usecase.auth.SignOutUseCase
import com.syncdev.domain.usecase.auth.doctor.LoginDoctorUseCase
import com.syncdev.domain.usecase.auth.doctor.RegisterDoctorUseCase
import com.syncdev.domain.usecase.auth.patient.LoginPatientUseCase
import com.syncdev.domain.usecase.auth.patient.RegisterPatientUseCase
import com.syncdev.domain.usecase.doctor.*
import com.syncdev.domain.usecase.doctor.appointments.CreateNewAppointmentUseCase
import com.syncdev.domain.usecase.doctor.appointments.GetAppointmentsByDoctorIdUseCase
import com.syncdev.domain.usecase.doctor.appointments.GetCompletedAppointmentsByDoctorIdUseCase
import com.syncdev.domain.usecase.doctor.appointments.GetUpcomingAppointmentsByDoctorIdUseCase
import com.syncdev.domain.usecase.doctor.appointments.UpdatePatientChronicDiseasesUseCase
import com.syncdev.domain.usecase.patient.SearchPatientByIdUseCase
import com.syncdev.domain.usecase.patient.appointment_requests.CreateAppointmentRequestUseCase
import com.syncdev.domain.usecase.patient.appointment_requests.DeleteAppointmentRequestUseCase
import com.syncdev.domain.usecase.patient.appointment_requests.GetAppointmentRequestsByDoctorIdUseCase
import com.syncdev.domain.usecase.patient.appointment_requests.GetPreservedAppointmentsDateUseCase
import com.syncdev.domain.usecase.patient.appointments.CancelAppointmentByIdUseCase
import com.syncdev.domain.usecase.patient.appointments.GetAppointmentsByPatientAndState
import com.syncdev.domain.usecase.patient.UpdatePatientByIdUseCase
import com.syncdev.domain.usecase.patient.appointments.GetCompletedAppointmentsByPatientIdUseCase
import com.syncdev.domain.usecase.patient.appointments.RescheduleAppointmentUseCase
import com.syncdev.domain.usecase.patient.appointments.UpdateDoctorRatingUseCase
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
    fun provideRegisterPatientUseCase(remoteRepository: RemoteRepository): RegisterPatientUseCase {
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

    @Provides
    fun provideSearchDoctorByIdUseCase(remoteRepository: RemoteRepository): SearchDoctorByIdUseCase {
        return SearchDoctorByIdUseCase(remoteRepository)
    }

    @Provides
    fun provideSearchPatientById(remoteRepository: RemoteRepository): SearchPatientByIdUseCase {
        return SearchPatientByIdUseCase(remoteRepository)
    }

    @Provides
    fun provideSignOutUseCase(remoteRepository: RemoteRepository): SignOutUseCase {
        return SignOutUseCase(remoteRepository)
    }

    @Provides
    fun provideGetAllDoctorUseCase(remoteRepository: RemoteRepository): GetAllDoctorsUseCase {
        return GetAllDoctorsUseCase(remoteRepository)
    }

    @Provides
    fun provideCreateAppointmentRequestUseCase(remoteRepository: RemoteRepository): CreateAppointmentRequestUseCase {
        return CreateAppointmentRequestUseCase(remoteRepository)
    }

    @Provides
    fun provideUpdateDoctorByIdUseCase(remoteRepository: RemoteRepository): UpdateDoctorByIdUseCase {
        return UpdateDoctorByIdUseCase(remoteRepository)
    }

    @Provides
    fun provideUpdatePatientByIdUseCase(remoteRepository: RemoteRepository):UpdatePatientByIdUseCase{
        return UpdatePatientByIdUseCase(remoteRepository)
    }

    @Provides
    fun provideGetAppointRequestsByDoctorIdUseCase(remoteRepository: RemoteRepository): GetAppointmentRequestsByDoctorIdUseCase {
        return GetAppointmentRequestsByDoctorIdUseCase(remoteRepository)
    }

    @Provides
    fun provideDeleteAppointmentRequestUseCase(remoteRepository: RemoteRepository): DeleteAppointmentRequestUseCase{
        return DeleteAppointmentRequestUseCase(remoteRepository)
    }

    @Provides
    fun provideCreateNewAppointmentUseCase(remoteRepository: RemoteRepository): CreateNewAppointmentUseCase{
        return CreateNewAppointmentUseCase(remoteRepository)
    }

    @Provides
    fun provideGetPreservedAppointmentsDateUseCase(remoteRepository: RemoteRepository): GetPreservedAppointmentsDateUseCase{
        return GetPreservedAppointmentsDateUseCase(remoteRepository)
    }

    @Provides
    fun provideGetAppointmentsByPatientAndState(remoteRepository: RemoteRepository): GetAppointmentsByPatientAndState{
        return GetAppointmentsByPatientAndState(remoteRepository)
    }

    @Provides
    fun provideCancelAppointmentByIdUseCase(remoteRepository: RemoteRepository): CancelAppointmentByIdUseCase{
        return CancelAppointmentByIdUseCase(remoteRepository)
    }

    @Provides
    fun provideUpdateDoctorRatingUseCase(remoteRepository: RemoteRepository): UpdateDoctorRatingUseCase{
        return UpdateDoctorRatingUseCase(remoteRepository)
    }

    @Provides
    fun provideRescheduleAppointmentUseCase(remoteRepository: RemoteRepository): RescheduleAppointmentUseCase{
        return RescheduleAppointmentUseCase(remoteRepository)
    }

    @Provides
    fun provideGetAppointmentsByDoctorUseCase(remoteRepository: RemoteRepository): GetAppointmentsByDoctorIdUseCase{
        return GetAppointmentsByDoctorIdUseCase(remoteRepository)
    }

    @Provides
    fun provideGetUpcomingAppointmentsUseCase(remoteRepository: RemoteRepository): GetUpcomingAppointmentsByDoctorIdUseCase{
        return GetUpcomingAppointmentsByDoctorIdUseCase(remoteRepository)
    }

    @Provides
    fun provideSavePrescriptionUseCase(remoteRepository: RemoteRepository):SavePrescriptionUseCase{
        return SavePrescriptionUseCase(remoteRepository)
    }

    @Provides
    fun provideGetPatientMedicalHistoryUseCase(remoteRepository: RemoteRepository):GetPatientMedicalHistoryUseCase{
        return GetPatientMedicalHistoryUseCase(remoteRepository)
    }

    @Provides
    fun updatePatientMedicalHistoryUseCase(remoteRepository: RemoteRepository):UpdatePatientMedicalHistoryUseCase{
        return UpdatePatientMedicalHistoryUseCase(remoteRepository)
    }

    @Provides
    fun updatePatientChronicDiseasesUseCase(remoteRepository: RemoteRepository):UpdatePatientChronicDiseasesUseCase{
        return UpdatePatientChronicDiseasesUseCase(remoteRepository)
    }

    @Provides
    fun provideGetCompletedAppointmentsByDoctorIdUseCase(remoteRepository: RemoteRepository): GetCompletedAppointmentsByDoctorIdUseCase{
        return GetCompletedAppointmentsByDoctorIdUseCase(remoteRepository)
    }

    @Provides
    fun provideGetCompletedAppointmentsByPatientIdUseCase(remoteRepository: RemoteRepository): GetCompletedAppointmentsByPatientIdUseCase{
        return GetCompletedAppointmentsByPatientIdUseCase(remoteRepository)
    }
}
