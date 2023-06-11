package com.syncdev.shifaa.ui.patient.profile.edit_profile

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.model.Patient
import com.syncdev.domain.usecase.patient.UpdatePatientByIdUseCase
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientEditProfileViewModel @Inject constructor(
    private val updatePatientByIdUseCase: UpdatePatientByIdUseCase,
    private val application: Application
) : ViewModel() {

    var updateState=MutableLiveData<Boolean>()
    var firstName=MutableLiveData<String>()
    var lastName=MutableLiveData<String>()
    var phoneNumber=MutableLiveData<String>()
    var age=MutableLiveData<String>()
    var patient=Patient()

    fun updatePatientData(){
        viewModelScope.launch {
            val patientData=getPatientDataFromFields()
            updateState.value=updatePatientByIdUseCase(patientData)!!
            if(updateState.value==true){
                SharedPreferencesUtils().savePatientToSharedPreferences(application,patient)
            }
        }
    }

    fun getPatientData(){
        val patientData=SharedPreferencesUtils().getPatientFromSharedPreferences(application)
        patientData?.let {
            patient.id=it.id
            firstName.value=it.firstName
            lastName.value=it.lastName
            phoneNumber.value=it.phoneNumber
            age.value=it.age
        }
    }

    private fun getPatientDataFromFields(): Patient {
        patient.firstName= firstName.value.toString()
        patient.lastName=lastName.value.toString()
        patient.phoneNumber=phoneNumber.value.toString()
        patient.age=age.value.toString()
        return patient
    }
}