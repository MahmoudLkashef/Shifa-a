package com.syncdev.shifaa.ui.auth.login.patient

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.syncdev.domain.usecase.auth.patient.LoginPatientUseCase
import com.syncdev.shifaa.utils.Constants
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientSignInViewModel
@Inject
constructor(
    private val loginPatientUseCase: LoginPatientUseCase,
    private val application: Application
) : ViewModel() {

    private val TAG = "PatientSignInViewModel"

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var firebaseUser = MutableLiveData<FirebaseUser>()
    private val _navigate = MutableLiveData(false)
    val navigate: LiveData<Boolean> get() = _navigate

    fun loginPatient() {
        viewModelScope.launch {
            try {
                firebaseUser.value = loginPatientUseCase
                    .invoke(email.value.toString(),password.value.toString())
                firebaseUser.value?.let {
                    SharedPreferencesUtils()
                        .saveUserInformationToSharedPreferences(application,it,Constants.PATIENT)
                    _navigate.value = true
                    Log.i(TAG, "loginPatient: login successfully ${it.email}")
                }
            } catch (e: Exception) {
                Log.i(TAG, "error: ${e.message}")
            }
        }
    }

}