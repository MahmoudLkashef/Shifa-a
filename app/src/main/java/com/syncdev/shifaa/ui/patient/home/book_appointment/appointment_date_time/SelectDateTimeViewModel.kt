package com.syncdev.shifaa.ui.patient.home.book_appointment.appointment_date_time

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.syncdev.domain.usecase.doctor.SearchDoctorByIdUseCase
import com.syncdev.shifaa.R
import com.syncdev.shifaa.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class SelectDateTimeViewModel
@Inject constructor(
    private val searchDoctorByIdUseCase: SearchDoctorByIdUseCase,
): ViewModel() {

    private val TAG = "SelectDateTimeViewModel"

    private var _appointmentTimeList = MutableLiveData<MutableList<String>>()
    val appointmentTimeList: LiveData<MutableList<String>> get() = _appointmentTimeList

    private var _date = MutableLiveData("")
    val date: LiveData<String> get() = _date

    private var _time = MutableLiveData("")
    val time: LiveData<String> get() = _time

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAppointmentTimeList(selectedDate: String){
        _appointmentTimeList.value?.clear()
        _appointmentTimeList.postValue(DateUtils.getAppointmentTimeList(selectedDate))
    }

    fun handleSelectedDate(date: Long): String {
        val format = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        return format.format(Date(date))
    }

    fun getCurrentDate(): Long{
        return Calendar.getInstance().timeInMillis
    }

    fun getMaxDate(): Long {
        // Calculate the date after 21 days
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 21)
        return calendar.timeInMillis
    }

    fun createChip(context: Context, time: String): Chip {
        val chip = Chip(context)
        chip.text = time
        chip.isClickable = true
        chip.isCheckable = true

        // Create a custom ChipDrawable with your desired style
        val chipDrawable = ChipDrawable.createFromAttributes(
            context,
            null,
            0,
            R.style.customChipStyle
        )

        // Set the custom ChipDrawable as the background for the chip
        chip.setChipDrawable(chipDrawable)

        chip.setOnCheckedChangeListener { _, isChecked ->
            // Set the text color of the chip to white when selected
            chip.setTextColor(
                if (isChecked) ContextCompat.getColor(context, android.R.color.white)
                else ContextCompat.getColor(context, R.color.chip_text_color)
            )

            if (isChecked) {
                val selectedTime = chip.text.toString()
                // Use the selected text as needed
                _time.value = selectedTime
            }
        }

        return chip
    }

    fun setDate(date: String){
        _date.value = date
    }

    fun validateData(): Boolean{
        return if (time.value?.isEmpty() == true || date.value?.isEmpty() == true){
            if (time.value?.isEmpty() == true) _errorMessage.value = "Please Select The Time"
            else if (date.value?.isEmpty() == true) _errorMessage.value = "Please Select The Date"
            false
        }else {
            true
        }
    }

}