package com.syncdev.domain.usecase.doctor

import com.syncdev.domain.model.Prescription
import com.syncdev.domain.repo.remote.RemoteRepository

class SavePrescriptionUseCase(private val remoteRepository: RemoteRepository) {
    suspend operator fun invoke(prescription: Prescription, appointmentId: String,patientId:String):Boolean =
        remoteRepository.savePrescription(prescription, appointmentId,patientId)
}