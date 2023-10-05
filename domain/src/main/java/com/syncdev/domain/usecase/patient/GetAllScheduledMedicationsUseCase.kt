package com.syncdev.domain.usecase.patient

import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class GetAllScheduledMedicationsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke() = mainRepository.getAllScheduledMedications()
}