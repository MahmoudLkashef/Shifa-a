package com.syncdev.domain.usecase.patient

import com.syncdev.domain.model.Patient
import com.syncdev.domain.repo.remote.RemoteRepository

class UpdatePatientByIdUseCase(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(patient:Patient):Boolean=remoteRepository.updatePatientDataById(patient)
}