package com.syncdev.domain.usecase.doctor

import com.syncdev.domain.model.Doctor
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class UpdateDoctorByIdUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(doctor: Doctor): Boolean =
        mainRepository.updateDoctorDataById(doctor)
}