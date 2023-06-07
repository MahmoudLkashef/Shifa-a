package com.syncdev.shifaa.ui.auth.register.patient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.syncdev.domain.model.Patient
import com.syncdev.domain.usecase.auth.patient.RegisterPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpPatientViewModel
@Inject
constructor(
    private val registerPatientUseCase: RegisterPatientUseCase
) : ViewModel() {

    private val TAG = "SignUpPatientViewModel"

    var firstName = MutableLiveData<String>()
    var lastName = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var phoneNumber = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var confirmPassword = MutableLiveData<String>()
    var day = MutableLiveData<String>()
    var month = MutableLiveData<String>()
    var year = MutableLiveData<String>()
    var gender = MutableLiveData<String>()
    var firebaseUser = MutableLiveData<FirebaseUser>()
    private val _navigate = MutableLiveData(false)
    val navigate: LiveData<Boolean> get() = _navigate

    fun signUpPatient(){
        viewModelScope.launch {
            try {
                firebaseUser.value = registerPatientUseCase
                    .invoke(getPatientData(),password.value.toString())
                firebaseUser.value?.let {
                    _navigate.value = true
                    Log.i(TAG, "loginPatient: login successfully ${it.displayName}")
                }
            }catch (e: Exception){
                Log.i(TAG, "signUpPatient: ${e.message}")
            }
        }
    }

    private fun getPatientData(): Patient {
        return Patient(
            firstName = firstName.value.toString(),
            lastName = lastName.value.toString(),
            age = getPatientAge(),
            gender = gender.value.toString(),
            phoneNumber = phoneNumber.value.toString(),
            email = email.value.toString()
        )
    }

    private fun getPatientAge(): String{
        return "${day.value.toString()}-${month.value.toString()}-${year.value.toString()}"
    }
}