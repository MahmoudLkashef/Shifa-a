package com.syncdev.domain.usecase.patient.appointment_requests

import com.syncdev.domain.repo.remote.RemoteRepository

class GetPreservedAppointmentsDateUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(
        doctorId: String,
        date: String
    ): List<String> = remoteRepository.getPreservedAppointmentsDate(doctorId, date)
}