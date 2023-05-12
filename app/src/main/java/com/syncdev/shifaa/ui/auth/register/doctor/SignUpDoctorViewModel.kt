package com.syncdev.shifaa.ui.auth.register.doctor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.usecase.auth.doctor.RegisterDoctorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpDoctorViewModel @Inject constructor(private val registerDoctorUseCase: RegisterDoctorUseCase) :
    ViewModel() {
    private val TAG = "SignUpDoctorViewModel"

    private val _navigate = MutableLiveData(false)
    val navigate: LiveData<Boolean> get() = _navigate
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val gender = MutableLiveData<String>()
    val speciality = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword=MutableLiveData<String>()


    fun registerDoctor(){
        viewModelScope.launch {
            try {
                val doctor=Doctor(firstName = firstName.value.toString(),
                    lastName = lastName.value.toString(),
                    gender = gender.value.toString(),
                    speciality = speciality.value.toString(),
                    phoneNumber = phoneNumber.value.toString(),
                    email = email.value.toString()
                )
               val firebaseUser= registerDoctorUseCase.invoke(doctor, password.value.toString())
                firebaseUser?.let {
                    _navigate.value=true
                    Log.i(TAG, "registerDoctor: ${firebaseUser.displayName}")
                }
            }catch (e:Exception){
                Log.e(TAG, "registerDoctor: ${e.message}")
            }
        }
    }
}
