package com.syncdev.shifaa.ui.doctor

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.usecase.doctor.SearchDoctorByIdUseCase
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorActivityViewModel
@Inject
    constructor(
    private val searchDoctorByIdUseCase: SearchDoctorByIdUseCase,
    private val application: Application
):ViewModel() {
    private val TAG = "DoctorActivityViewModel"

    fun searchDoctorById(doctorId: String){
        if (SharedPreferencesUtils().getDoctorFromSharedPreferences(application)==null) {
            viewModelScope.launch {
                try {
                    searchDoctorByIdUseCase.invoke(doctorId) { doctor ->
                        if (doctor != null) {
                            SharedPreferencesUtils()
                                .saveDoctorToSharedPreferences(application, doctor)
                        } else {
                            Log.i(TAG, "searchDoctorById: Doctor was not found")
                        }
                    }
                } catch (e: Exception) {
                    Log.i(TAG, "searchDoctorById: ${e.message}")
                }
            }
        }
    }

    fun getDoctorId(): String? {
        return SharedPreferencesUtils().getFirebaseUserFromSharedPreferences(application)
    }
}