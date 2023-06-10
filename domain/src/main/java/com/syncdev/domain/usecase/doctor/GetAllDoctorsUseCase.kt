package com.syncdev.domain.usecase.doctor

import com.google.firebase.database.DatabaseError
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.repo.remote.RemoteRepository

class GetAllDoctorsUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(
        callback: (List<Doctor>?, DatabaseError?) -> Unit
    ) = remoteRepository.fetchDoctorsFromFirebase(callback)
}