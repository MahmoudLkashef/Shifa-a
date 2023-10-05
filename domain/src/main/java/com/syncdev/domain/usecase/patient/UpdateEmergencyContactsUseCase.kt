package com.syncdev.domain.usecase.patient

import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class UpdateEmergencyContactsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(patientId: String, emergencyContacts: List<String>): Boolean =
        mainRepository.updateEmergencyContacts(patientId, emergencyContacts)
}