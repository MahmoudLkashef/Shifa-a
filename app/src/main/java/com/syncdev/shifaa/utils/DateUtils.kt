package com.syncdev.shifaa.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.time.LocalDate
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
}