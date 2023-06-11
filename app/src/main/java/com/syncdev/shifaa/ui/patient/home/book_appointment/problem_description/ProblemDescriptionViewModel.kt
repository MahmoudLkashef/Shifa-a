package com.syncdev.shifaa.ui.patient.home.book_appointment.problem_description

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.AppointmentRequest
import com.syncdev.domain.model.Patient
import com.syncdev.domain.usecase.patient.appointment_requests.CreateAppointmentRequestUseCase
import com.syncdev.domain.usecase.patient.appointment_requests.DeleteAppointmentRequestUseCase
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProblemDescriptionViewModel
@Inject constructor(
    private val createAppointmentRequestUseCase: CreateAppointmentRequestUseCase,
    private val application: Application
): ViewModel() {

    private val TAG = "ProblemDescriptionViewModel"

    val problemComment = MutableLiveData("")

    private var _navigate = MutableLiveData(false)
    val navigate: LiveData<Boolean> get() = _navigate

    private val sharedPreferencesUtils = SharedPreferencesUtils()

    private fun getPatient(): Patient{
        return sharedPreferencesUtils.getPatientFromSharedPreferences(application)!!
    }

    fun createAppointmentRequest(appointmentRequest: AppointmentRequest){
        viewModelScope.launch {
            _navigate.value = createAppointmentRequestUseCase.invoke(appointmentRequest)
        }
    }

    fun gatherData(doctorId: String, time: String, date: String): AppointmentRequest{
        return AppointmentRequest(
            patient = getPatient(),
            doctorId = doctorId,
            time = time,
            date = date,
            comment = problemComment.value!!
        )
    }

}