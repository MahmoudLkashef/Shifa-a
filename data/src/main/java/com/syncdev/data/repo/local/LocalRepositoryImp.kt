package com.syncdev.data.repo.local

import com.syncdev.data.local.ScheduledMedicationDao
import com.syncdev.domain.model.ScheduledMedication
import com.syncdev.domain.repo.local.LocalRepository
import javax.inject.Inject

class LocalRepositoryImp @Inject constructor(
    private val scheduledMedicationDao: ScheduledMedicationDao
) :LocalRepository {

    override suspend fun insertScheduledMedications(scheduledMedications: List<ScheduledMedication>) {
        scheduledMedicationDao.insertScheduledMedications(scheduledMedications)
    }
}