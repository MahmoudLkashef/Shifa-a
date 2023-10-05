package com.syncdev.domain.usecase.patient.appointments

import com.syncdev.domain.model.Appointment
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class GetAppointmentsByPatientAndState @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        patientId: String,
        state: String
    ): List<Appointment> = mainRepository.getAppointmentsByPatientAndState(patientId, state)
}