package com.syncdev.domain.usecase.doctor.appointments

import com.syncdev.domain.repo.remote.RemoteRepository

class UpdatePatientChronicDiseasesUseCase(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(patientId: String, chronicDiseases: List<String>): Boolean =
        remoteRepository.updatePatientChronicDiseases(patientId, chronicDiseases)
}