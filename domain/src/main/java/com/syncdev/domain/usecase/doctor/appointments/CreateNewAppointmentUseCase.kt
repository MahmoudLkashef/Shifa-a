package com.syncdev.domain.usecase.doctor.appointments

import com.syncdev.domain.model.Appointment
import com.syncdev.domain.repo.remote.RemoteRepository

class CreateNewAppointmentUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(
        appointment: Appointment
    ): Boolean = remoteRepository.createNewAppointment(appointment)
}