package com.syncdev.domain.usecase.doctor

import com.google.firebase.database.DatabaseError
import com.syncdev.domain.model.Doctor
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class GetAllDoctorsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        callback: (List<Doctor>?, DatabaseError?) -> Unit
    ) = mainRepository.fetchDoctorsFromFirebase(callback)
}