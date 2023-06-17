package com.syncdev.domain.usecase.doctor.appointments

import com.syncdev.domain.model.Appointment
import com.syncdev.domain.repo.remote.RemoteRepository

class GetCompletedAppointmentsByDoctorIdUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(
        doctorId: String
    ): List<Appointment> = remoteRepository.getCompletedAppointmentsByDoctorId(doctorId)
}