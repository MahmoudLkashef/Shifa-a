package com.syncdev.shifaa.ui.patient

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.usecase.patient.SearchPatientByIdUseCase
import com.syncdev.shifaa.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientActivityViewModel
@Inject
constructor(
    private val searchPatientByIdUseCase: SearchPatientByIdUseCase,
    private val application: Application
): ViewModel() {
    private val TAG = "PatientActivityViewModel"

    fun searchPatientById(patientId: String){
        if (SharedPreferencesUtils().getPatientFromSharedPreferences(application)==null) {
            viewModelScope.launch {
                try {
                    searchPatientByIdUseCase.invoke(patientId) { patient ->
                        if (patient != null) {
                            SharedPreferencesUtils()
                                .savePatientToSharedPreferences(application, patient)
                        } else {
                            Log.i(TAG, "searchPatientById: patient was not found")
                        }
                    }
                } catch (e: Exception) {
                    Log.i(TAG, "searchPatientById: ${e.message}")
                }
            }
        }
    }

    fun getPatientId(): String? {
        return SharedPreferencesUtils().getFirebaseUserFromSharedPreferences(application)
    }
}