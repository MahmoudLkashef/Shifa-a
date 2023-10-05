package com.syncdev.shifaa.ui.doctor.patients.new_patients.request_details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.Appointment
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.model.Patient
import com.syncdev.domain.usecase.doctor.appointments.CreateNewAppointmentUseCase
import com.syncdev.domain.usecase.patient.SearchPatientByIdUseCase
import com.syncdev.domain.usecase.patient.appointment_requests.DeleteAppointmentRequestUseCase
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AppointmentRequestDetailsViewModel
@Inject constructor(
    private val deleteAppointmentRequestUseCase: DeleteAppointmentRequestUseCase,
    private val searchPatientByIdUseCase: SearchPatientByIdUseCase,
    private val createNewAppointmentUseCase: CreateNewAppointmentUseCase,
    private val application: Application
): ViewModel(){

    private var _navigate = MutableLiveData<Boolean>()
    val navigate : LiveData<Boolean> get() = _navigate

    private var _patient = MutableLiveData<Patient>()
    val patient: LiveData<Patient> get() = _patient

    private var _saved = MutableLiveData<Boolean>()
    val saved : LiveData<Boolean> get() = _saved

    fun getDoctor(): Doctor{
        return SharedPreferencesUtils().getDoctorFromSharedPreferences(application)!!
    }

    fun getPatient(patientId: String){
        viewModelScope.launch {
            searchPatientByIdUseCase.invoke(patientId) {
                _patient.postValue(it)
            }
        }
    }

    fun deleteAppointmentRequest(doctorId: String, date: String, time: String){
        viewModelScope.launch {
            _navigate.value = deleteAppointmentRequestUseCase.invoke(doctorId, date, time)
        }
    }

    fun createNewAppointment(appointment: Appointment){
        viewModelScope.launch {
            _saved.value = createNewAppointmentUseCase.invoke(appointment)
        }
    }

    fun gatherDataForAppointment(comment: String,time: String,date: String): Appointment{
        return Appointment(
            patient = patient.value!!,
            doctor = getDoctor(),
            comment = comment,
            time = time,
            date = date
        )
    }
}