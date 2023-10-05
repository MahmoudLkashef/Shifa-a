package com.syncdev.shifaa.ui.doctor.dashboard

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.Appointment
import com.syncdev.domain.model.AppointmentRequest
import com.syncdev.domain.usecase.doctor.GetAppointmentsUseCase
import com.syncdev.shifaa.utils.DateUtils
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorDashboardViewModel @Inject constructor(
    private val getAppointmentsUseCase: GetAppointmentsUseCase,
    private val application: Application
) : ViewModel() {

    private val _upcomingAppointments = MutableLiveData<List<Appointment>>()
    val upcomingAppointments: LiveData<List<Appointment>> get() = _upcomingAppointments

    private val _appointmentsRequests = MutableLiveData<List<AppointmentRequest>>()
    val appointmentsRequests: LiveData<List<AppointmentRequest>> get() = _appointmentsRequests

    private val _completedAppointments = MutableLiveData<List<Appointment>>()
    val completedAppointments: LiveData<List<Appointment>> get() = _completedAppointments

    fun getTodayAppointments() {
        viewModelScope.launch {
            val today = DateUtils.getCurrentDate()
            getDoctorId()?.let { doctorId ->
                _upcomingAppointments.postValue(
                    getAppointmentsUseCase.getUpcomingAppointmentsByDoctorIdUseCase.invoke(doctorId, today)
                )
            }
        }
    }

    fun getAppointmentsRequests() {
        viewModelScope.launch {
            getDoctorId()?.let { doctorId ->
                _appointmentsRequests.postValue(
                    getAppointmentsUseCase.getAppointmentRequestsByDoctorIdUseCase.invoke(doctorId)
                )
            }
        }
    }

    fun getCompletedAppointments() {
        viewModelScope.launch {
            getDoctorId()?.let { doctorId ->
                _completedAppointments.postValue(
                    getAppointmentsUseCase.getCompletedAppointmentsByDoctorId.invoke(doctorId)
                )
            }
        }
    }

    private fun getDoctorId(): String {
        return SharedPreferencesUtils().getDoctorFromSharedPreferences(application)?.id!!
    }
}