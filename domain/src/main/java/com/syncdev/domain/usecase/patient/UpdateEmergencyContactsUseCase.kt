package com.syncdev.domain.usecase.patient

import com.syncdev.domain.repo.remote.RemoteRepository

class UpdateEmergencyContactsUseCase(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(patientId: String, emergencyContacts: List<String>): Boolean =
        remoteRepository.updateEmergencyContacts(patientId, emergencyContacts)
}