package com.syncdev.shifaa.ui.doctor.patients.new_patients

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.AppointmentRequest
import com.syncdev.domain.usecase.patient.appointment_requests.DeleteAppointmentRequestUseCase
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import com.syncdev.domain.usecase.patient.appointment_requests.GetAppointmentRequestsByDoctorIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DoctorNewPatientsViewModel
@Inject constructor
    (
    private val getAppointmentRequestsByDoctorIdUseCase: GetAppointmentRequestsByDoctorIdUseCase,
    private val deleteAppointmentRequestUseCase: DeleteAppointmentRequestUseCase,
    private val application: Application
): ViewModel() {

    private val TAG = "DoctorNewPatientsViewModel"

    private var _appointmentRequests = MutableLiveData<List<AppointmentRequest>>()
    val appointmentRequests: LiveData<List<AppointmentRequest>> get() = _appointmentRequests

    private var _updateList = MutableLiveData<Boolean>()
    val updateList: LiveData<Boolean> get() = _updateList

    private fun getDoctorId(): String{
        return SharedPreferencesUtils().getDoctorFromSharedPreferences(application)?.id.toString()
    }

    fun fetchAppointmentRequests(){
        val doctorId = getDoctorId()
        _updateList.value = false
        viewModelScope.launch {
            val list = getAppointmentRequestsByDoctorIdUseCase.invoke(doctorId)
            _appointmentRequests.postValue(list)
        }
    }


    fun deleteAppointmentRequest(doctorId: String, date: String, time: String){
        viewModelScope.launch {
            _updateList.value = deleteAppointmentRequestUseCase.invoke(doctorId, date, time)
        }
    }
}