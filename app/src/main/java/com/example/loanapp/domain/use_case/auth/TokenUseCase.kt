package com.example.loanapp.domain.use_case.auth

import com.example.loanapp.data.local.repository.LoginDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//TODO: юзкейс должен выпонять одну операцию. Т.е. данный юзкейс можно разделить на 3
class TokenUseCase @Inject constructor(private val loginDataStoreRepository: LoginDataStoreRepository) {

    suspend fun saveToken(token: String) = loginDataStoreRepository.saveToken(token)

    suspend fun removeToken() = loginDataStoreRepository.removeToken()

    fun getSavedToken(): Flow<String> = loginDataStoreRepository.savedToken

}