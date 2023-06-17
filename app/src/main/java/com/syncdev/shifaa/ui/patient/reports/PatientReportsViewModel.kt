package com.syncdev.shifaa.ui.patient.reports

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.Appointment
import com.syncdev.domain.usecase.patient.appointments.GetCompletedAppointmentsByPatientIdUseCase
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientReportsViewModel
@Inject
constructor(
    private val application: Application,
    private val getCompletedAppointmentsByPatientIdUseCase: GetCompletedAppointmentsByPatientIdUseCase
): ViewModel(){

    private var _reports = MutableLiveData<List<Appointment>>()
    val reports: LiveData<List<Appointment>> get() = _reports

    private fun getPatientId(): String{
        return SharedPreferencesUtils().getPatientFromSharedPreferences(application)?.id!!
    }

    fun getReports(){
        viewModelScope.launch {
            _reports.postValue(
                getCompletedAppointmentsByPatientIdUseCase.invoke(getPatientId())
            )
        }
    }

}