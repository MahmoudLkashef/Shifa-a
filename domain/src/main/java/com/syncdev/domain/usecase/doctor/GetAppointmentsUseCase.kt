package com.syncdev.domain.usecase.doctor

import com.syncdev.domain.usecase.doctor.appointments.GetCompletedAppointmentsByDoctorIdUseCase
import com.syncdev.domain.usecase.doctor.appointments.GetUpcomingAppointmentsByDoctorIdUseCase
import com.syncdev.domain.usecase.patient.appointment_requests.GetAppointmentRequestsByDoctorIdUseCase
import javax.inject.Inject

data class GetAppointmentsUseCase @Inject constructor(
    val getUpcomingAppointmentsByDoctorIdUseCase: GetUpcomingAppointmentsByDoctorIdUseCase,
    val getCompletedAppointmentsByDoctorId: GetCompletedAppointmentsByDoctorIdUseCase,
    val getAppointmentRequestsByDoctorIdUseCase:GetAppointmentRequestsByDoctorIdUseCase
)