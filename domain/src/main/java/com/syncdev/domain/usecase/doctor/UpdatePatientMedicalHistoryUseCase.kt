package com.syncdev.domain.usecase.doctor

import com.syncdev.domain.model.MedicalHistory
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class UpdatePatientMedicalHistoryUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(patientId: String, medicalHistory: MedicalHistory): Boolean =
        mainRepository.updatePatientMedicalHistory(patientId, medicalHistory)
}