package com.syncdev.shifaa.ui.auth.login.patient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.syncdev.domain.usecase.auth.patient.LoginPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientSignInViewModel
@Inject
constructor(
    private val loginPatientUseCase: LoginPatientUseCase
) : ViewModel() {

    private val TAG = "PatientSignInViewModel"

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    private val _navigate = MutableLiveData(false)
    val navigate: LiveData<Boolean> get() = _navigate

    fun loginPatient() {
        viewModelScope.launch {
            try {
                val firebaseUser = loginPatientUseCase
                    .invoke(email.value.toString(),password.value.toString())
                firebaseUser?.let { 
                    _navigate.value = true
                    Log.i(TAG, "loginPatient: login successfully ${it.displayName}")
                }
            } catch (e: Exception) {
                Log.i(TAG, "error: ${e.message}")
            }
        }
    }

}