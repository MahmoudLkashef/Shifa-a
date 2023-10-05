package com.syncdev.domain.usecase.doctor

import com.syncdev.domain.model.Doctor
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class SearchDoctorByIdUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(
        doctorId: String,
        onDoctorLoaded: (Doctor?) -> Unit
    ) = mainRepository.searchDoctorById(doctorId, onDoctorLoaded)
}