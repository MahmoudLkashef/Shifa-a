package com.syncdev.shifaa.ui.doctor.home.prescription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.Medication
import com.syncdev.domain.model.Prescription
import com.syncdev.domain.usecase.doctor.SavePrescriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrescriptionViewModel @Inject constructor(private val savePrescriptionUseCase: SavePrescriptionUseCase) :
    ViewModel() {

    private val TAG = "PrescriptionViewModel"
    private val _medication = MutableLiveData<ArrayList<Medication>>()
    val medicationList: LiveData<ArrayList<Medication>> get() = _medication
    private val _savedState = MutableLiveData<Boolean>()
    val savedState: LiveData<Boolean> get() = _savedState

    fun updateMedicationList(medication: Medication) {
        val currentList = _medication.value ?: ArrayList()
        currentList.add(medication)
        _medication.postValue(currentList)
    }

    fun savePatientPrescription(
        prescription: Prescription,
        appointmentId: String,
        patientId: String
    ) {
        viewModelScope.launch {
            _savedState.postValue(savePrescriptionUseCase.invoke(prescription, appointmentId,patientId))
        }
    }

}