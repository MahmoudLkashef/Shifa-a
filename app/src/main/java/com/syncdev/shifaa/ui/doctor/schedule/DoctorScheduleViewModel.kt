package com.syncdev.shifaa.ui.doctor.schedule

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.Appointment
import com.syncdev.domain.model.CalendarModel
import com.syncdev.domain.usecase.doctor.appointments.GetAppointmentsByDoctorIdUseCase
import com.syncdev.domain.usecase.patient.appointments.CancelAppointmentByIdUseCase
import com.syncdev.shifaa.utils.DateUtils
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DoctorScheduleViewModel
@Inject
constructor(
    private val getAppointmentsByDoctorIdUseCase: GetAppointmentsByDoctorIdUseCase,
    private val cancelAppointmentByIdUseCase: CancelAppointmentByIdUseCase,
    private val application: Application
) : ViewModel() {

    private var _availableDays = MutableLiveData<List<CalendarModel>>()
    val availableDays: LiveData<List<CalendarModel>> get() = _availableDays

    private var _appointments = MutableLiveData<List<Appointment>>()
    val appointments: LiveData<List<Appointment>> get() = _appointments

    private val _filteredAppointments = MutableLiveData<List<Appointment>>()
    val filteredAppointments: LiveData<List<Appointment>> get() = _filteredAppointments

    private var _updateList = MutableLiveData<Boolean>()
    val updateList: LiveData<Boolean> get() = _updateList

    var chosenDate = MutableLiveData(DateUtils.getCurrentDate())

    private fun getDoctorId(): String{
        return SharedPreferencesUtils().getDoctorFromSharedPreferences(application)?.id!!
    }

    fun getAppointmentsByDoctorId(){
        viewModelScope.launch {
            val appointments = getAppointmentsByDoctorIdUseCase.invoke(getDoctorId())
            _appointments.value = appointments // Update the live data first

            // After updating the live data, retrieve the appointments for the current date
            getAppointmentsByDate(chosenDate.value!!)
        }
    }

    fun getAppointmentsByDate(date: String){
        _filteredAppointments.postValue(
            appointments.value?.filter { it.date == chosenDate.value }
        )
    }

    fun validateModifyingAppointment(today: String, appointmentDate: String): Boolean{
        return today != appointmentDate
    }

    fun cancelAppointmentById(appointmentId: String){
        viewModelScope.launch {
            _updateList.postValue(cancelAppointmentByIdUseCase.invoke(appointmentId))
        }
    }

    fun getAvailableDaysList(){
        _availableDays.value = DateUtils.getUpcoming21Days()
    }
}