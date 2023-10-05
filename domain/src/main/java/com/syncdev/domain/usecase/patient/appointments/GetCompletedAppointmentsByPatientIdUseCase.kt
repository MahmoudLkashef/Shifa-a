package com.syncdev.domain.usecase.patient.appointments

import com.syncdev.domain.model.Appointment
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class GetCompletedAppointmentsByPatientIdUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        patientId: String
    ): List<Appointment> = mainRepository.getCompletedAppointmentsByPatientId(patientId)
}