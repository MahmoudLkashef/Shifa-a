package com.syncdev.domain.usecase.patient.appointment_requests

import com.syncdev.domain.model.AppointmentRequest
import com.syncdev.domain.repo.remote.RemoteRepository

class CreateAppointmentRequestUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(
        appointmentRequest: AppointmentRequest
    ): Boolean = remoteRepository.createAppointmentRequest(appointmentRequest)

}