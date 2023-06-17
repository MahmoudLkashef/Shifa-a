package com.syncdev.domain.usecase.doctor

import com.syncdev.domain.model.MedicalHistory
import com.syncdev.domain.repo.remote.RemoteRepository

class UpdatePatientMedicalHistoryUseCase(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(patientId: String, medicalHistory: MedicalHistory): Boolean =
        remoteRepository.updatePatientMedicalHistory(patientId, medicalHistory)
}