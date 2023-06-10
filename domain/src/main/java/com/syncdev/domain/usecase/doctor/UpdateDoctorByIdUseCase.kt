package com.syncdev.domain.usecase.doctor

import com.syncdev.domain.model.Doctor
import com.syncdev.domain.repo.remote.RemoteRepository

class UpdateDoctorByIdUseCase(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(doctor: Doctor):Boolean = remoteRepository.updateDoctorDataById(doctor)
}