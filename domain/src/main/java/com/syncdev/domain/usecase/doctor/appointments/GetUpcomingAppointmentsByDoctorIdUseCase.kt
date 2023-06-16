package com.syncdev.domain.usecase.doctor.appointments

import com.syncdev.domain.model.Appointment
import com.syncdev.domain.repo.remote.RemoteRepository

class GetUpcomingAppointmentsByDoctorIdUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(
        doctorId: String,
        date: String
    ): List<Appointment> = remoteRepository.getAppointmentsByDoctorAndDate(doctorId, date)
}