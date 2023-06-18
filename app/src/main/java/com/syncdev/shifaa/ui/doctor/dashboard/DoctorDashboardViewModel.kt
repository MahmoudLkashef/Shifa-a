package com.syncdev.shifaa.ui.doctor.dashboard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.usecase.doctor.appointments.GetUpcomingAppointmentsByDoctorIdUseCase
import com.syncdev.shifaa.utils.DateUtils
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorDashboardViewModel @Inject constructor(
    private val getUpcomingAppointmentsByDoctorIdUseCase: GetUpcomingAppointmentsByDoctorIdUseCase,
    private val application: Application
) : ViewModel() {

    //private val appointmentsList

    fun getTodayAppointments() {
        viewModelScope.launch {
            val today = DateUtils.getCurrentDate()
            getDoctorId()?.let {doctorId->
                getUpcomingAppointmentsByDoctorIdUseCase.invoke(doctorId,today)
            }
        }
    }

    private fun getDoctorId(): String {
        return SharedPreferencesUtils().getDoctorFromSharedPreferences(application)?.id!!
    }
}