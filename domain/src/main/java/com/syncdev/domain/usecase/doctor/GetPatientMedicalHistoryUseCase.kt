package com.syncdev.domain.usecase.doctor

import com.syncdev.domain.model.MedicalHistory
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class GetPatientMedicalHistoryUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(patientId: String,callback: (MedicalHistory?) -> Unit)=
        mainRepository.getPatientMedicalHistory(patientId,callback)
}