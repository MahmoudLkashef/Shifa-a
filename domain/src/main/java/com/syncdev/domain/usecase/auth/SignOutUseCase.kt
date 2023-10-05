package com.syncdev.domain.usecase.auth

import com.syncdev.domain.repo.MainRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke() = mainRepository.signOut()
}