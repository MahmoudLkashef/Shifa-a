package com.syncdev.domain.usecase.patient.appointments

import com.syncdev.domain.repo.remote.RemoteRepository

class UpdateDoctorRatingUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(
        doctorId: String,
        newRating: Float
    ) = remoteRepository.updateDoctorRating(doctorId, newRating)
}