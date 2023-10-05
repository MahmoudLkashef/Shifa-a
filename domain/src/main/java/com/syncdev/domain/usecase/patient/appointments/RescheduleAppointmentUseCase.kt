package com.syncdev.domain.usecase.patient.appointments

import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class RescheduleAppointmentUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        appointmentId: String,
        date: String,
        time: String
    ): Boolean = mainRepository.rescheduleAppointment(appointmentId, date, time)
}