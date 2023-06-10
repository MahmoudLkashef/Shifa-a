package com.syncdev.shifaa.ui.patient.home.find_doctor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.usecase.doctor.GetAllDoctorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FindDoctorViewModel
@Inject constructor(
    private val getAllDoctorsUseCase: GetAllDoctorsUseCase
): ViewModel() {
    private val TAG = "FindDoctorViewModel"

    private var _doctorsList = MutableLiveData<List<Doctor>>()
    val doctorsList: LiveData<List<Doctor>> get() = _doctorsList

    private var _filteredList = MutableLiveData<List<Doctor>>()
    val filteredList: LiveData<List<Doctor>> get() = _filteredList

    fun getAllDoctors(){
        viewModelScope.launch {
            getAllDoctorsUseCase.invoke(){doctors, error ->
                if (error != null) {
                    // Handle the error
                    Log.i(TAG, "Error fetching doctors: ${error.message}")
                } else {
                    if (doctors != null) {
                        // Use the retrieved list of doctors here
                        _doctorsList.postValue(doctors)
                        _filteredList.postValue(doctors)
                    } else {
                        // Handle the case when no doctors are available
                        Log.i(TAG, "getAllDoctors: No Doctors Found!!")
                    }
                }
            }
        }
    }

    fun filterDoctorsList(specialty: String){
        if (specialty == "All Specialities"){
            _filteredList.value = doctorsList.value
        } else {
            _filteredList.value = doctorsList.value?.filter { it.speciality == specialty }
        }
    }


}