package com.syncdev.domain.local

import com.syncdev.domain.model.ScheduledMedication

interface LocalDataSource {
    suspend fun insertScheduledMedications(scheduledMedications: List<ScheduledMedication>)

    suspend fun getAllScheduledMedications(): List<ScheduledMedication>

    suspend fun getScheduledMedicationById(scheduledMedicationId: Int): ScheduledMedication
}