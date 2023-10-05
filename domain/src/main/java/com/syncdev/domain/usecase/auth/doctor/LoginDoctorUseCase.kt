package com.syncdev.domain.usecase.auth.doctor

import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

/**
 * Use case for logging in a doctor.
 *
 * This class provides a simplified and testable way to log in a doctor by calling the [MainRepository]
 * which handles the actual network request.
 *
 * @param mainRepository The [MainRepository] dependency used to make the network request.
 */
class LoginDoctorUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    /**
     * Logs in a doctor.
     *
     * @param email The email of the doctor to log in.
     * @param password The password of the doctor to log in.
     *
     * @return A [Result] object representing the result of the network request.
     * The [Result] can contain either a successful [Doctor] object or an [Exception] object if there was an error.
     */
    suspend operator fun invoke(
        email: String,
        password: String
    ) = mainRepository.loginDoctor(email, password)
}
