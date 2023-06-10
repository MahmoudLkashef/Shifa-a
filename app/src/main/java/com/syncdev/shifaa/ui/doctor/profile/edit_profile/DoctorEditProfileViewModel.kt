package com.syncdev.shifaa.ui.doctor.profile.edit_profile

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.usecase.doctor.UpdateDoctorByIdUseCase
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorEditProfileViewModel @Inject constructor(
    private val updateDoctorByIdUseCase: UpdateDoctorByIdUseCase,
    private val application: Application
) : ViewModel() {
    val TAG = "DoctorEditProfileViewModel"
    var updateState=MutableLiveData<Boolean>(false)
    var firstName=MutableLiveData<String>()
    var lastName=MutableLiveData<String>()
    var phoneNumber=MutableLiveData<String>()
    var yearsOfExperience=MutableLiveData<String>()
    var specialty=MutableLiveData<String>()
    var aboutDoctor=MutableLiveData<String>()
    private val doctor=Doctor()

    fun getDoctorData() {
        val doctorData = SharedPreferencesUtils().getDoctorFromSharedPreferences(application)
        doctorData?.let {
            doctor.id=it.id
            firstName.value=it.firstName
            lastName.value=it.lastName
            phoneNumber.value=it.phoneNumber
            yearsOfExperience.value=it.yearsOfExperience.toString()
            specialty.value=it.speciality
            aboutDoctor.value=it.aboutDoctor
        }
    }

    fun updateDoctorData() {
        viewModelScope.launch {
            val doctor=getDoctorDataFromFields()
            updateState.value= updateDoctorByIdUseCase(doctor)!!
            if(updateState.value == true){
                SharedPreferencesUtils().saveDoctorToSharedPreferences(application, doctor)
            }
        }
    }

    private fun getDoctorDataFromFields():Doctor{
        doctor.firstName= firstName.value.toString()
        doctor.lastName=lastName.value.toString()
        doctor.phoneNumber=phoneNumber.value.toString()
        doctor.speciality=specialty.value.toString()
        doctor.yearsOfExperience= yearsOfExperience.value?.toInt() ?: 0
        doctor.aboutDoctor=aboutDoctor.value.toString()
        return doctor
    }

}