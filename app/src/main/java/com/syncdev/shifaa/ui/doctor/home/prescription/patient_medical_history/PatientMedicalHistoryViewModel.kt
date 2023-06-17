package com.syncdev.shifaa.ui.doctor.home.prescription.patient_medical_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.MedicalHistory
import com.syncdev.domain.usecase.doctor.GetPatientMedicalHistoryUseCase
import com.syncdev.domain.usecase.doctor.UpdatePatientMedicalHistoryUseCase
import com.syncdev.domain.usecase.doctor.appointments.UpdatePatientChronicDiseasesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientMedicalHistoryViewModel @Inject constructor(
    private val getPatientMedicalHistoryUseCase:
    GetPatientMedicalHistoryUseCase,
    private val updatePatientMedicalHistoryUseCase: UpdatePatientMedicalHistoryUseCase,
    private val updatePatientChronicDiseasesUseCase: UpdatePatientChronicDiseasesUseCase
) :
    ViewModel() {
    private val _patientMedicalHistoryData = MutableLiveData<MedicalHistory>()
    val patientMedicalHistoryData: LiveData<MedicalHistory> get() = _patientMedicalHistoryData

    fun getPatientMedicalHistory(patientId: String) {
        viewModelScope.launch {
            getPatientMedicalHistoryUseCase.invoke(patientId) { medicalHistory ->
                _patientMedicalHistoryData.postValue(medicalHistory)
            }
        }
    }

    fun updatePatientMedicalHistory(patientId: String, medicalHistory: MedicalHistory) {
        viewModelScope.launch {
            updatePatientMedicalHistoryUseCase.invoke(patientId, medicalHistory)
        }
    }

    fun updatePatientChronicDiseases(patientId:String,chronicDiseases:List<String>){
        viewModelScope.launch{
            updatePatientChronicDiseasesUseCase.invoke(patientId, chronicDiseases)
        }
    }
}