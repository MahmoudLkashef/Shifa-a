package com.syncdev.shifaa.ui.patient.reports

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.Appointment
import com.syncdev.domain.model.Medication
import com.syncdev.domain.model.ScheduledMedication
import com.syncdev.domain.usecase.patient.InsertScheduledMedicationsUseCase
import com.syncdev.domain.usecase.patient.appointments.GetCompletedAppointmentsByPatientIdUseCase
import com.syncdev.shifaa.utils.Conversion
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientReportsViewModel
@Inject
constructor(
    private val application: Application,
    private val getCompletedAppointmentsByPatientIdUseCase: GetCompletedAppointmentsByPatientIdUseCase,
    private val insertScheduledMedicationsUseCase: InsertScheduledMedicationsUseCase
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

    fun convertMedicationsToScheduledMedications(medications: List<Medication>): List<ScheduledMedication>{
        return Conversion.createScheduledMedicine(medications)
    }

    fun insertScheduledMedication(scheduledMedications: List<ScheduledMedication>){
        viewModelScope.launch {
            insertScheduledMedicationsUseCase.invoke(scheduledMedications)
        }
    }

}