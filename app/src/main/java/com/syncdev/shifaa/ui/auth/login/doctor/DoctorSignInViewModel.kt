package com.syncdev.shifaa.ui.auth.login.doctor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.usecase.auth.doctor.LoginDoctorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorSignInViewModel @Inject constructor(private val loginDoctorUseCase: LoginDoctorUseCase) :
    ViewModel() {
    private val TAG = "DoctorSignInViewModel"

    private val _navigate = MutableLiveData(false)
    val navigate: LiveData<Boolean> get() = _navigate
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()


    fun loginDoctor() {
        viewModelScope.launch {
            try {
                val firebaseUser=loginDoctorUseCase.invoke(email.value.toString(), password.value.toString())
                firebaseUser?.let { _navigate.value=true }
                Log.i(TAG, "loginDoctor: ${firebaseUser?.isAnonymous}")
            } catch (e: Exception) {
                Log.e(TAG, "loginDoctor:${e.message} ")
            }
        }
    }

}