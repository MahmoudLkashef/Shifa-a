package com.syncdev.domain.usecase.patient

import com.syncdev.domain.model.ScheduledMedication
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class GetScheduledMedicationByIdUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        scheduledMedicationId: Int
    ): ScheduledMedication = mainRepository.getScheduledMedicationById(scheduledMedicationId)
}