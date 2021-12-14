package com.example.loanapp.domain.use_case.auth

import com.example.loanapp.data.local.repository.TokenDataStoreRepository
import javax.inject.Inject

class RemoveTokenUseCase @Inject constructor(private val loginDataStoreRepository: TokenDataStoreRepository) {

    suspend operator fun invoke() = loginDataStoreRepository.removeToken()

}