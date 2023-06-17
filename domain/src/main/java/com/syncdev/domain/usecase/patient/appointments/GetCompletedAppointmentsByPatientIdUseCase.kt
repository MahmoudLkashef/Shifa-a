package com.syncdev.domain.usecase.patient.appointments

import com.syncdev.domain.model.Appointment
import com.syncdev.domain.repo.remote.RemoteRepository

class GetCompletedAppointmentsByPatientIdUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(
        patientId: String
    ): List<Appointment> = remoteRepository.getCompletedAppointmentsByPatientId(patientId)
}