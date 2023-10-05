package com.syncdev.domain.usecase.doctor.appointments

import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class UpdatePatientChronicDiseasesUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(patientId: String, chronicDiseases: List<String>): Boolean =
        mainRepository.updatePatientChronicDiseases(patientId, chronicDiseases)
}