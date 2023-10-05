package com.syncdev.domain.usecase.doctor.appointments

import com.syncdev.domain.model.Appointment
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class GetUpcomingAppointmentsByDoctorIdUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        doctorId: String,
        date: String
    ): List<Appointment> = mainRepository.getAppointmentsByDoctorAndDate(doctorId, date)
}