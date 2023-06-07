package com.syncdev.domain.usecase.patient

import com.syncdev.domain.model.Patient
import com.syncdev.domain.repo.remote.RemoteRepository

class SearchPatientByIdUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(
        patientId: String,
        onPatientLoaded: (Patient?) -> Unit
    ) = remoteRepository.searchPatientById(patientId, onPatientLoaded)
}