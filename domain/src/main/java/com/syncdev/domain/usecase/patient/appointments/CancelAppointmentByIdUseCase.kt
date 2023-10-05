package com.syncdev.domain.usecase.patient.appointments

import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class CancelAppointmentByIdUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        appointmentId: String
    ): Boolean = mainRepository.cancelAppointmentById(appointmentId)
}