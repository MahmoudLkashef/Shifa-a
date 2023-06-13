package com.syncdev.domain.usecase.patient.appointments

import com.syncdev.domain.repo.remote.RemoteRepository

class CancelAppointmentByIdUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(
        appointmentId: String
    ): Boolean = remoteRepository.cancelAppointmentById(appointmentId)
}