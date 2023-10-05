package com.syncdev.domain.usecase.doctor.appointments

import com.syncdev.domain.model.Appointment
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class GetAppointmentsByDoctorIdUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        doctorId: String
    ): List<Appointment> = mainRepository.getAppointmentsByDoctorId(doctorId)
}