package com.syncdev.domain.usecase.patient

import com.syncdev.domain.model.ScheduledMedication
import com.syncdev.domain.repo.local.LocalRepository

class InsertScheduledMedicationsUseCase(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(
        scheduledMedication: List<ScheduledMedication>
    ) = localRepository.insertScheduledMedications(scheduledMedication)
}