package com.syncdev.domain.usecase.patient.appointment_requests

import com.syncdev.domain.model.AppointmentRequest
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class CreateAppointmentRequestUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        appointmentRequest: AppointmentRequest
    ): Boolean = mainRepository.createAppointmentRequest(appointmentRequest)

}