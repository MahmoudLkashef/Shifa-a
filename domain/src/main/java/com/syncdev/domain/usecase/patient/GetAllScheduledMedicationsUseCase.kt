package com.syncdev.domain.usecase.patient

import com.syncdev.domain.repo.local.LocalRepository

class GetAllScheduledMedicationsUseCase(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke() = localRepository.getAllScheduledMedications()
}