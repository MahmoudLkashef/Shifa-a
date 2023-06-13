package com.syncdev.shifaa.ui.patient.appointments

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import com.syncdev.domain.model.Appointment
import com.syncdev.domain.usecase.patient.appointments.CancelAppointmentByIdUseCase
import com.syncdev.domain.usecase.patient.appointments.GetAppointmentsByPatientAndState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentsViewModel
@Inject constructor(
    private val getAppointmentsByPatientAndState: GetAppointmentsByPatientAndState,
    private val cancelAppointmentByIdUseCase: CancelAppointmentByIdUseCase,
    private val application: Application
):ViewModel() {
    private val TAG = "AppointmentsViewModel"

    private var _upcomingAppointmentsList = MutableLiveData<List<Appointment>>()
    val upcomingAppointmentsList: LiveData<List<Appointment>> get() = _upcomingAppointmentsList

    private var _completedAppointmentsList = MutableLiveData<List<Appointment>>()
    val completedAppointmentsList: LiveData<List<Appointment>> get() = _completedAppointmentsList

    private var _canceledAppointmentsList = MutableLiveData<List<Appointment>>()
    val canceledAppointmentsList: LiveData<List<Appointment>> get() = _canceledAppointmentsList

    private var _updateList = MutableLiveData<Boolean>()
    val updateList: LiveData<Boolean> get() = _updateList

    private fun getPatientId(): String{
        return SharedPreferencesUtils().getPatientFromSharedPreferences(application)?.id!!
    }


    fun getAppointmentsByState(state: String){
        _updateList.value = false
        viewModelScope.launch {
            when(state){
                "Upcoming" ->{
                    _upcomingAppointmentsList.postValue(
                        getAppointmentsByPatientAndState.invoke(getPatientId(),state)
                    )
                }
                "Completed" ->{
                    _completedAppointmentsList.postValue(
                        getAppointmentsByPatientAndState.invoke(getPatientId(),state)
                    )
                }
                "Canceled" ->{
                    _canceledAppointmentsList.postValue(
                        getAppointmentsByPatientAndState.invoke(getPatientId(),state)
                    )
                }
            }

        }
    }

    fun cancelAppointmentById(appointmentId: String){
        viewModelScope.launch {
            _updateList.postValue(cancelAppointmentByIdUseCase.invoke(appointmentId))
        }
    }

}