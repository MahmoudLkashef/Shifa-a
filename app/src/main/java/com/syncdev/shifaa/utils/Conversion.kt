package com.syncdev.shifaa.utils

import com.syncdev.domain.model.Medication
import com.syncdev.domain.model.ScheduledMedication

object Conversion {
    fun createScheduledMedicine(medications: List<Medication>): List<ScheduledMedication>{
        val scheduledMedications = mutableListOf<ScheduledMedication>()
        medications.forEach { medication ->
            val startDate = DateUtils.getCurrentDate()
            val endDate = DateUtils.calculateEndDate(startDate,medication.period.toInt())
            if (medication.frequency.isNotEmpty()){
                val frequency = medication.frequency.toInt()
                val times = DateUtils.calculateTimes(frequency)

                for (i in 0 until frequency){
                    val scheduledMedication = ScheduledMedication(
                        medication.id.toInt()+i,
                        medication.name,
                        medication.type,
                        medication.dosage,
                        startDate,
                        endDate,
                        medication.frequency,
                        times[i],
                        medication.scheduleLabels
                    )
                    scheduledMedications.add(scheduledMedication)
                }
            }
        }
        return scheduledMedications
    }
}