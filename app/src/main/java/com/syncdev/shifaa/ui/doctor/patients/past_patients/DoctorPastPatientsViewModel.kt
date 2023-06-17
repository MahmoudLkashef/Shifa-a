package com.syncdev.shifaa.ui.doctor.patients.past_patients

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.Appointment
import com.syncdev.domain.usecase.doctor.appointments.GetCompletedAppointmentsByDoctorIdUseCase
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorPastPatientsViewModel
@Inject
constructor(
    private val application: Application,
    private val getCompletedAppointmentsByDoctorIdUseCase: GetCompletedAppointmentsByDoctorIdUseCase
):ViewModel() {

    private var _completedAppointments = MutableLiveData<List<Appointment>>()
    val completedAppointments: LiveData<List<Appointment>> get() = _completedAppointments

    private fun getDoctorId(): String{
        return SharedPreferencesUtils().getDoctorFromSharedPreferences(application)?.id!!
    }

    fun getCompletedAppointments(){
        viewModelScope.launch {
            _completedAppointments.postValue(
                getCompletedAppointmentsByDoctorIdUseCase.invoke(getDoctorId())
            )
        }
    }
}