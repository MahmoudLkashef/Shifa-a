package com.syncdev.domain.usecase.auth.doctor

import com.syncdev.domain.model.Doctor
import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

/**
 * Use case for registering a new doctor.
 *
 * This class provides a simplified and testable way to register a new doctor by calling the [MainRepository]
 * which handles the actual network request.
 *
 * @param mainRepository The [MainRepository] dependency used to make the network request.
 */
class RegisterDoctorUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    /**
     * Registers a new doctor.
     *
     * @param doctor The [Doctor] object representing the new doctor to register.
     * @param password The password to use for the new doctor's account.
     *
     * @return A [Result] object representing the result of the network request.
     * The [Result] can contain either a successful [Doctor] object or an [Exception] object if there was an error.
     */
    suspend operator fun invoke(
        doctor: Doctor,
        password: String
    ) = mainRepository.registerDoctor(doctor, password)
}
