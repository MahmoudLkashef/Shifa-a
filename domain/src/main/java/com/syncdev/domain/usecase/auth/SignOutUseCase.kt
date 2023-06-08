package com.syncdev.domain.usecase.auth

import com.syncdev.domain.repo.remote.RemoteRepository

class SignOutUseCase(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke() = remoteRepository.signOut()
}