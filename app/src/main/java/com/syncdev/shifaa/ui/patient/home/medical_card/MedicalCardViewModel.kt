package com.syncdev.shifaa.ui.patient.home.medical_card

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.MedicalHistory
import com.syncdev.domain.usecase.doctor.GetPatientMedicalHistoryUseCase
import com.syncdev.domain.usecase.patient.UpdateEmergencyContactsUseCase
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicalCardViewModel @Inject constructor(
    private val getPatientMedicalHistoryUseCase: GetPatientMedicalHistoryUseCase,
    private val updateEmergencyContactsUseCase: UpdateEmergencyContactsUseCase,
    private val application: Application
) : ViewModel() {

    private val _patientMedicalHistory = MutableLiveData<MedicalHistory>()
    val patientMedicalHistory: LiveData<MedicalHistory> get() = _patientMedicalHistory

    fun getPatientMedicalHistory() {
        viewModelScope.launch {
            getPatientId()?.let { patientId ->
                getPatientMedicalHistoryUseCase.invoke(patientId) {
                    _patientMedicalHistory.postValue(it)
                }
            }
        }
    }

    fun updateEmergencyContacts(emergencyContacts:List<String>){
        viewModelScope.launch {
            updateEmergencyContactsUseCase.invoke(getPatientId(), emergencyContacts)
        }
    }

    private fun getPatientId(): String {
        return SharedPreferencesUtils().getPatientFromSharedPreferences(application)?.id!!
    }

    fun getPatientName(): String {
        val firstName =
            SharedPreferencesUtils().getPatientFromSharedPreferences(application)?.firstName!!
        val lastName =
            SharedPreferencesUtils().getPatientFromSharedPreferences(application)?.lastName!!
        return "$firstName $lastName"
    }
}