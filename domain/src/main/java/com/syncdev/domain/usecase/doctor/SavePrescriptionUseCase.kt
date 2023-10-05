package com.syncdev.domain.usecase.doctor

import com.syncdev.domain.model.Prescription
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class SavePrescriptionUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(prescription: Prescription, appointmentId: String,patientId:String):Boolean =
        mainRepository.savePrescription(prescription, appointmentId,patientId)
}