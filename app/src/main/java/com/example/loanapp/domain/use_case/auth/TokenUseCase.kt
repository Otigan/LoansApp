package com.example.loanapp.domain.use_case.auth

import com.example.loanapp.data.local.repository.LoginDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenUseCase @Inject constructor(private val loginDataStoreRepository: LoginDataStoreRepository) {

    suspend fun saveToken(token: String) = loginDataStoreRepository.saveToken(token)

    fun getSavedToken(): Flow<String> = loginDataStoreRepository.savedToken

}