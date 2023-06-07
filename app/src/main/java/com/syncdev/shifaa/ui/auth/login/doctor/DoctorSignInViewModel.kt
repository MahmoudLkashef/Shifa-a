package com.syncdev.shifaa.ui.auth.login.doctor

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.usecase.auth.doctor.LoginDoctorUseCase
import com.syncdev.shifaa.utils.Constants
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorSignInViewModel @Inject constructor(
    private val loginDoctorUseCase: LoginDoctorUseCase,
    private val application: Application
    ) :
    ViewModel() {
    private val TAG = "DoctorSignInViewModel"

    private val _navigate = MutableLiveData(false)
    val navigate: LiveData<Boolean> get() = _navigate
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var firebaseUser = MutableLiveData<FirebaseUser>()


    fun loginDoctor() {
        viewModelScope.launch {
            try {
                firebaseUser.value = loginDoctorUseCase
                    .invoke(email.value.toString(), password.value.toString())
                firebaseUser.value?.let {
                    SharedPreferencesUtils()
                        .saveUserInformationToSharedPreferences(application,it,Constants.DOCTOR)
                    _navigate.value=true
                    Log.i(TAG, "loginDoctor: ${it.isAnonymous}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "loginDoctor:${e.message} ")
            }
        }
    }

}