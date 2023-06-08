package com.syncdev.shifaa.ui.doctor.profile

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.usecase.auth.SignOutUseCase
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DoctorProfileViewModel
@Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val application: Application
): ViewModel() {

    val signedOut = MutableLiveData(false)
    val doctor = MutableLiveData<Doctor>()
    private val TAG = "DoctorProfileViewModel"

    fun signOut(){
        val sharedPreferencesUtils = SharedPreferencesUtils()
        viewModelScope.launch {
            signOutUseCase.invoke()
            sharedPreferencesUtils.apply {
                deleteFirebaseUserFromSharedPreferences(application)
                deleteDoctorFromSharedPreferences(application)
            }
            signedOut.value = true
        }
    }

    fun getDoctorData(){
        doctor.value = SharedPreferencesUtils()
            .getDoctorFromSharedPreferences(application)
    }

    fun getFullName(): String{
        return doctor.value?.firstName + " " + doctor.value?.lastName
    }
}