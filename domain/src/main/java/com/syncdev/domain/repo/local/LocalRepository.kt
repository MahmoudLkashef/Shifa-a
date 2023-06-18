package com.syncdev.domain.repo.local

import com.syncdev.domain.model.ScheduledMedication

interface LocalRepository {
    suspend fun insertScheduledMedications(scheduledMedications: List<ScheduledMedication>)

    suspend fun getAllScheduledMedications(): List<ScheduledMedication>

    suspend fun getScheduledMedicationById(scheduledMedicationId: Int): ScheduledMedication
}