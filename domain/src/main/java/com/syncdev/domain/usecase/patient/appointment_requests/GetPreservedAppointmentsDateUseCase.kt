package com.syncdev.domain.usecase.patient.appointment_requests

import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class GetPreservedAppointmentsDateUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        doctorId: String,
        date: String
    ): List<String> = mainRepository.getPreservedAppointmentsDate(doctorId, date)
}