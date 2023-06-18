package com.syncdev.domain.usecase.patient

import com.syncdev.domain.model.ScheduledMedication
import com.syncdev.domain.repo.local.LocalRepository

class GetScheduledMedicationByIdUseCase(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(
        scheduledMedicationId: Int
    ): ScheduledMedication = localRepository.getScheduledMedicationById(scheduledMedicationId)
}