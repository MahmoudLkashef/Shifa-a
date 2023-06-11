package com.syncdev.domain.usecase.patient.appointment_requests

import com.syncdev.domain.repo.remote.RemoteRepository

class DeleteAppointmentRequestUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(
        doctorId: String,
        date: String,
        time: String
    ): Boolean = remoteRepository.deleteAppointmentRequest(doctorId, date, time)
}