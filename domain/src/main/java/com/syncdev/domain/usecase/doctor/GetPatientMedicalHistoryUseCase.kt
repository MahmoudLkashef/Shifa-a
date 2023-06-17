package com.syncdev.domain.usecase.doctor

import com.syncdev.domain.model.MedicalHistory
import com.syncdev.domain.repo.remote.RemoteRepository

class GetPatientMedicalHistoryUseCase(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(patientId: String,callback: (MedicalHistory?) -> Unit)=
        remoteRepository.getPatientMedicalHistory(patientId,callback)
}