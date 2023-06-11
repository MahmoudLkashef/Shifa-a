package com.syncdev.domain.usecase.patient.appointment_requests

import com.syncdev.domain.model.AppointmentRequest
import com.syncdev.domain.repo.remote.RemoteRepository

class GetAppointmentRequestsByDoctorIdUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(
        doctorId: String
    ): List<AppointmentRequest> = remoteRepository.getAppointmentRequestsByDoctorId(doctorId)
}