package com.syncdev.domain.repo.local

import com.syncdev.domain.model.ScheduledMedication

interface LocalRepository {
    suspend fun insertScheduledMedications(scheduledMedications: List<ScheduledMedication>)
}