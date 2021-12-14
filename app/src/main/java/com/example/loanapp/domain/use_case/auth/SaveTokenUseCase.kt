package com.example.loanapp.domain.use_case.auth

import com.example.loanapp.data.local.repository.TokenDataStoreRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(private val loginDataStoreRepository: TokenDataStoreRepository) {

    suspend operator fun invoke(token: String) = loginDataStoreRepository.saveToken(token)

}