package com.syncdev.domain.usecase.patient.appointments

import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class UpdateDoctorRatingUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        doctorId: String,
        newRating: Float
    ) = mainRepository.updateDoctorRating(doctorId, newRating)
}