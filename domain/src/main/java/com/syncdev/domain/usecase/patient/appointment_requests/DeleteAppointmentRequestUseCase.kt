package com.syncdev.domain.usecase.patient.appointment_requests

import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class DeleteAppointmentRequestUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        doctorId: String,
        date: String,
        time: String
    ): Boolean = mainRepository.deleteAppointmentRequest(doctorId, date, time)
}