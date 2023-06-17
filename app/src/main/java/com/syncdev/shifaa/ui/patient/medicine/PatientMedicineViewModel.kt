package com.syncdev.shifaa.ui.patient.medicine

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syncdev.domain.model.CalendarModel
import com.syncdev.shifaa.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PatientMedicineViewModel
@Inject
constructor(
    private val application: Application
): ViewModel(){
    private var _availableDays = MutableLiveData<List<CalendarModel>>()
    val availableDays: LiveData<List<CalendarModel>> get() = _availableDays


    fun getAvailableDaysList(){
        _availableDays.value = DateUtils.getUpcoming21Days()
    }
}