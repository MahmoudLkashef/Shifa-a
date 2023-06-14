package com.syncdev.shifaa.ui.patient.home.book_appointment.appointment_details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.model.Patient
import com.syncdev.domain.usecase.doctor.SearchDoctorByIdUseCase
import com.syncdev.domain.usecase.patient.appointments.RescheduleAppointmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookAppointmentDetailsViewModel
@Inject constructor(
    private val searchDoctorByIdUseCase: SearchDoctorByIdUseCase,
    private val rescheduleAppointmentUseCase: RescheduleAppointmentUseCase,
    private val application: Application
): ViewModel(){

    private val TAG = "BookAppointmentDetailsViewModel"

    private var _doctor = MutableLiveData<Doctor>()
    val doctor: LiveData<Doctor> get() = _doctor

    private var _date = MutableLiveData("")
    val date: LiveData<String> get() = _date

    private var _time = MutableLiveData("")
    val time: LiveData<String> get() = _time

    private var _navigateBack = MutableLiveData(false)
    val navigateBack: LiveData<Boolean> get() = _navigateBack

    fun getDoctorById(doctorId: String){
        viewModelScope.launch {
            searchDoctorByIdUseCase.invoke(doctorId){_doctor.value = it}
        }
    }

    fun rescheduleAppointment(appointmentId: String){
        viewModelScope.launch {
            _navigateBack.postValue(rescheduleAppointmentUseCase.invoke(
                appointmentId,
                date = date.value!!,
                time = time.value!!
            ))
        }
    }

    fun getFullName(fName: String, lName: String): String{
        return "$fName $lName"
    }

    fun getRate(totalRating: Float, numOfReviews: Int): String {
        return if (numOfReviews == 0){
            "No Rate"
        }else {
            val rate = totalRating / numOfReviews
            String.format("%.1f", rate)
        }
    }

    fun setTime(time: String?){
        _time.value = time
    }

    fun setDate(date: String?){
        _date.value = date
    }

    fun haveDateAndTime(): Boolean{
        return (time.value?.isNotEmpty() == true && date.value?.isNotEmpty() == true)
    }

}