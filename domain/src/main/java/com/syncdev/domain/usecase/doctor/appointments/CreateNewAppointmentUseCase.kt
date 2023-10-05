package com.syncdev.domain.usecase.doctor.appointments

import com.syncdev.domain.model.Appointment
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class CreateNewAppointmentUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        appointment: Appointment
    ): Boolean = mainRepository.createNewAppointment(appointment)
}