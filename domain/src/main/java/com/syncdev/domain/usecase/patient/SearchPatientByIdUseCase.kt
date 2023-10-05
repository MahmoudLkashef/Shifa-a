package com.syncdev.domain.usecase.patient

import com.syncdev.domain.model.Patient
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class SearchPatientByIdUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        patientId: String,
        onPatientLoaded: (Patient?) -> Unit
    ) = mainRepository.searchPatientById(patientId, onPatientLoaded)
}