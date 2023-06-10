package com.syncdev.shifaa.ui.doctor.schedule

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syncdev.domain.model.CalendarModel
import com.syncdev.shifaa.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DoctorScheduleViewModel
@Inject
constructor(
    private val application: Application
) : ViewModel() {

    val availableDays = MutableLiveData<List<CalendarModel>>()

    fun getAvailableDaysList(){
        availableDays.value = DateUtils.getUpcoming21Days()
    }
}