package com.syncdev.shifaa.ui.doctor.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syncdev.domain.model.Medication

class PrescriptionViewModel:ViewModel() {
    private val TAG="PrescriptionViewModel"
    private val _medication=MutableLiveData<ArrayList<Medication>>()
    val medicationList:LiveData<ArrayList<Medication>> get() = _medication


    fun updateMedicationList(medication: Medication){
        val currentList =_medication.value ?: ArrayList()
        currentList.add(medication)
        _medication.postValue(currentList)
        Log.i(TAG, "updateMedicationList: ${medicationList.value?.size}")
    }

}