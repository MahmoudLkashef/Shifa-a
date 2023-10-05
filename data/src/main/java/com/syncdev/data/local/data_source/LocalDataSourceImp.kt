package com.syncdev.data.local.data_source

import com.syncdev.data.local.ScheduledMedicationDao
import com.syncdev.domain.local.LocalDataSource
import com.syncdev.domain.model.ScheduledMedication
import javax.inject.Inject

class LocalDataSourceImp @Inject constructor(
    private val scheduledMedicationDao: ScheduledMedicationDao
) : LocalDataSource {

    override suspend fun insertScheduledMedications(scheduledMedications: List<ScheduledMedication>) {
        scheduledMedicationDao.insertScheduledMedications(scheduledMedications)
    }

    override suspend fun getAllScheduledMedications(): List<ScheduledMedication> {
        return scheduledMedicationDao.getAllScheduledMedications()
    }

    override suspend fun getScheduledMedicationById(scheduledMedicationId: Int): ScheduledMedication {
        return scheduledMedicationDao.getScheduledMedicationById(scheduledMedicationId)
    }
}