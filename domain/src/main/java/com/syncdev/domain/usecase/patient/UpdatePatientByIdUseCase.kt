package com.syncdev.domain.usecase.patient

import com.syncdev.domain.model.Patient
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class UpdatePatientByIdUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(patient: Patient): Boolean =
        mainRepository.updatePatientDataById(patient)
}