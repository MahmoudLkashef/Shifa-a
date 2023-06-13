package com.syncdev.domain.usecase.patient.appointments

import com.syncdev.domain.model.Appointment
import com.syncdev.domain.repo.remote.RemoteRepository

class GetAppointmentsByPatientAndState(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(
        patientId: String,
        state: String
    ): List<Appointment> = remoteRepository.getAppointmentsByPatientAndState(patientId, state)
}