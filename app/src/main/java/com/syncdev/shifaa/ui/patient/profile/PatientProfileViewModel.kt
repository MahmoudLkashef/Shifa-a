package com.syncdev.shifaa.ui.patient.profile

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.Patient
import com.syncdev.domain.usecase.auth.SignOutUseCase
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientProfileViewModel
@Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val application: Application
) : ViewModel() {

    val signedOut = MutableLiveData(false)
    val patient = MutableLiveData<Patient>()
    private val TAG = "PatientProfileViewModel"

    fun signOut() {
        val sharedPreferencesUtils = SharedPreferencesUtils()
        viewModelScope.launch {
            signOutUseCase.invoke()
            sharedPreferencesUtils.apply {
                deleteFirebaseUserFromSharedPreferences(application)
                deletePatientFromSharedPreferences(application)
            }
            signedOut.value = true
        }
    }

    fun getPatientData() {
        patient.value = SharedPreferencesUtils()
            .getPatientFromSharedPreferences(application)
    }

    fun getFullName(): String {
        return patient.value?.firstName + " " + patient.value?.lastName
    }
}