package com.example.loanapp.domain.use_case.auth

import com.example.loanapp.data.local.repository.TokenDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val loginDataStoreRepository: TokenDataStoreRepository) {

    operator fun invoke(): Flow<String> = loginDataStoreRepository.savedToken

}