package com.syncdev.shifaa.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import com.syncdev.domain.model.CalendarModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLast100Years(): List<String> {
        val currentYear = LocalDate.now().year
        val lastYear = currentYear - 99
        val formatter = if (Locale.getDefault().language == "ar") {
            NumberFormat.getInstance(Locale("ar"))
        } else {
            NumberFormat.getIntegerInstance(Locale.getDefault())
        }
        formatter.isGroupingUsed=false // to remove decimal separator
        return (lastYear..currentYear).map { formatter.format(it) }.reversed()
    }

    fun get31DayList(): List<String> {
        val language = Locale.getDefault().language
        val formatter = if (language == "ar") {
            NumberFormat.getInstance(Locale("ar"))
        } else {
            NumberFormat.getInstance(Locale.getDefault())
        }
        return (1..31).map { formatter.format(it) }
    }

    fun getUpcoming21Days(): List<CalendarModel> {
        val calendarModelList = mutableListOf<CalendarModel>()

        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val calendarModel = Calendar.getInstance()

        for (i in 0 until 21) {
            val dayOfMonth = String.format("%02d", calendarModel.get(Calendar.DAY_OF_MONTH))
            val dayOfWeek = SimpleDateFormat("EEE", Locale.getDefault()).format(calendarModel.time)
            val date = dateFormat.format(calendarModel.time)

            val calendarModelItem = CalendarModel(i, dayOfMonth, dayOfWeek, date)
            calendarModelList.add(calendarModelItem)

            calendarModel.add(Calendar.DAY_OF_MONTH, 1)
        }

        return calendarModelList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAppointmentTimeList(selectedDate: String): MutableList<String> {
        val timeSlots = mutableListOf<String>()

        val startTime = LocalTime.of(9, 0,0,0)  // Starting time: 09:00 AM
        val endTime = LocalTime.of(16, 0,0,0) // Ending time: 04:00 PM

        val formatter = DateTimeFormatter.ofPattern("hh:mm a")

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(calendar.time)
        val currentTime = LocalTime.now()

        if (selectedDate == currentDate) {
            var currentTimeSlot = startTime
            while (currentTimeSlot.isBefore(endTime)) {
                if (currentTimeSlot.isAfter(currentTime)) {
                    val formattedTime = currentTimeSlot.format(formatter)
                    timeSlots.add(formattedTime)
                }
                currentTimeSlot = currentTimeSlot.plusMinutes(30)
            }
        } else {
            var currentTimeSlot = startTime
            while (currentTimeSlot.isBefore(endTime)) {
                val formattedTime = currentTimeSlot.format(formatter)
                timeSlots.add(formattedTime)
                currentTimeSlot = currentTimeSlot.plusMinutes(30)
            }
        }

        return timeSlots
    }

    fun getMonthNumber(monthName: String): String {
        return when (monthName) {
            "January" -> "1"
            "February" -> "2"
            "March" -> "3"
            "April" -> "4"
            "May" -> "5"
            "June" -> "6"
            "July" -> "7"
            "August" -> "8"
            "September" -> "9"
            "October" -> "10"
            "November" -> "11"
            "December" -> "12"
            else -> ""
        }
    }

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

    fun getCurrentDateNumber(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

    private fun getCurrentTime(): String {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = Date()
        return timeFormat.format(currentTime)
    }

}