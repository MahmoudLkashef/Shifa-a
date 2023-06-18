package com.syncdev.shifaa.ui.patient.medicine

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syncdev.domain.model.CalendarModel
import com.syncdev.domain.model.ScheduledMedication
import com.syncdev.domain.usecase.patient.GetAllScheduledMedicationsUseCase
import com.syncdev.shifaa.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PatientMedicineViewModel
@Inject
constructor(
    private val getAllScheduledMedicationsUseCase: GetAllScheduledMedicationsUseCase
): ViewModel(){
    private var _availableDays = MutableLiveData<List<CalendarModel>>()
    val availableDays: LiveData<List<CalendarModel>> get() = _availableDays

    private var allMedicine = MutableLiveData<List<ScheduledMedication>>()

    private var _medicineByDay = MutableLiveData<List<ScheduledMedication>?>()
    val medicineByDay: LiveData<List<ScheduledMedication>?> get() = _medicineByDay

    var chosenDate = MutableLiveData(DateUtils.getCurrentDate())

    fun getAvailableDaysList(){
        _availableDays.value = DateUtils.getUpcoming21Days()
    }

    fun getAllMedicine(){
        viewModelScope.launch{
            val result = getAllScheduledMedicationsUseCase.invoke()
            allMedicine.value = result

            filterMedicationsByEndDate(chosenDate.value!!)
        }
    }

    fun filterMedicationsByEndDate(date: String) {
        val formatter = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val filterDate = formatter.parse(date)

        val filteredMedications = allMedicine.value?.filter { medication ->
            val endDate = formatter.parse(medication.endDate)
            endDate?.let { it.compareTo(filterDate) >= 0 } ?: false
        }?.sortedBy { medication ->
            val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
            timeFormatter.parse(medication.time)
        }

        _medicineByDay.value = filteredMedications
    }

}