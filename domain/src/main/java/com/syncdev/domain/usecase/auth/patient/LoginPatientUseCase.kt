package com.syncdev.domain.usecase.auth.patient

import com.syncdev.domain.repo.remote.RemoteRepository
import javax.inject.Inject

/**
 * Use case for logging in a patient.
 *
 * This class provides a simplified and testable way to log in a patient by calling the [RemoteRepository]
 * which handles the actual network request.
 *
 * @param remoteRepository The [RemoteRepository] dependency used to make the network request.
 */
class LoginPatientUseCase(
    private val remoteRepository: RemoteRepository
) {
    /**
     * Logs in a patient.
     *
     * @param email The email of the patient to log in.
     * @param password The password of the patient to log in.
     *
     * @return A [Result] object representing the result of the network request.
     * The [Result] can contain either a successful [Patient] object or an [Exception] object if there was an error.
     */
    suspend operator fun invoke(
        email: String,
        password: String
    ) = remoteRepository.loginPatient(email, password)
}
