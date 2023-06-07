package com.syncdev.domain.usecase.doctor

import com.syncdev.domain.model.Doctor
import com.syncdev.domain.repo.remote.RemoteRepository

class SearchDoctorByIdUseCase(
    private val remoteRepository: RemoteRepository
) {

    suspend operator fun invoke(
        doctorId: String,
        onDoctorLoaded: (Doctor?) -> Unit
    ) = remoteRepository.searchDoctorById(doctorId, onDoctorLoaded)
}