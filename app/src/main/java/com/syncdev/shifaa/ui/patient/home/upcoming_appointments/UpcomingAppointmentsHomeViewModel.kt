package com.syncdev.shifaa.ui.patient.home.upcoming_appointments

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.Appointment
import com.syncdev.domain.usecase.patient.appointments.GetAppointmentsByPatientAndState
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingAppointmentsHomeViewModel @Inject constructor(
    private val getAppointmentsByPatientAndState: GetAppointmentsByPatientAndState,
    private val application: Application
) : ViewModel() {

    private val TAG = "UpcomingAppointmentsHomeViewModel"
    private var _upcomingAppointments = MutableLiveData<List<Appointment>>()
    val upcomingAppointments: LiveData<List<Appointment>> get() = _upcomingAppointments

    fun getUpcomingAppointments() {
        viewModelScope.launch {
            getPatientId()?.let {
                _upcomingAppointments.postValue(
                    getAppointmentsByPatientAndState.invoke(getPatientId()!!, "Upcoming")
                )
            }
        }
    }

    private fun getPatientId(): String? {
        return SharedPreferencesUtils().getPatientFromSharedPreferences(application)?.id
    }
}