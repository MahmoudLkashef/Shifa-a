package com.syncdev.shifaa.ui.doctor.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.Appointment
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.usecase.doctor.appointments.GetUpcomingAppointmentsByDoctorIdUseCase
import com.syncdev.shifaa.utils.DateUtils

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorHomeViewModel
@Inject constructor(
    private val application: Application,
    private val getUpcomingAppointmentsByDoctorIdUseCase: GetUpcomingAppointmentsByDoctorIdUseCase
): ViewModel() {

    private var _doctor = MutableLiveData<Doctor?>()
    val doctor: LiveData<Doctor?> get() = _doctor

    private var _upcomingAppointments = MutableLiveData<List<Appointment>>()
    val upcomingAppointments: LiveData<List<Appointment>> get() = _upcomingAppointments

    fun getDoctor(){
        viewModelScope.launch {
            val doctor = SharedPreferencesUtils().getDoctorFromSharedPreferences(application)
            _doctor.postValue(doctor)
        }
    }

    fun getTodayAppointments(){
        viewModelScope.launch {
            val today = DateUtils.getCurrentDate()
            _upcomingAppointments.postValue(
                getUpcomingAppointmentsByDoctorIdUseCase.invoke(
                    doctor.value?.id!!,
                    today
                )
            )
        }
    }

}