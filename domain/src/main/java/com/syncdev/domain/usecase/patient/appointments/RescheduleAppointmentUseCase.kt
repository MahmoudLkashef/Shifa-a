package com.syncdev.domain.usecase.patient.appointments

import com.syncdev.domain.repo.remote.RemoteRepository

class RescheduleAppointmentUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(
        appointmentId: String,
        date: String,
        time: String
    ): Boolean = remoteRepository.rescheduleAppointment(appointmentId, date, time)
}