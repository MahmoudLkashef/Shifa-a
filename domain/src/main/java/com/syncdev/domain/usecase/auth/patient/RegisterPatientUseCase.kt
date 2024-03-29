package com.syncdev.domain.usecase.auth.patient

import com.syncdev.domain.model.Patient
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

/**
 * Use case for registering a new patient.
 *
 * This class provides a simplified and testable way to register a new patient by calling the [MainRepository]
 * which handles the actual network request.
 *
 * @param mainRepository The [MainRepository] dependency used to make the network request.
 */
class RegisterPatientUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    /**
     * Registers a new patient.
     *
     * @param patient The [Patient] object representing the new patient to register.
     * @param password The password to use for the new patient's account.
     *
     * @return A [Result] object representing the result of the network request.
     * The [Result] can contain either a successful [Patient] object or an [Exception] object if there was an error.
     */
    suspend operator fun invoke(
        patient: Patient,
        password: String
    ) = mainRepository.registerPatient(patient, password)
}
