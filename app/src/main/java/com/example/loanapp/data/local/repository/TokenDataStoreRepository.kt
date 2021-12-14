package com.example.loanapp.data.local.repository

import com.example.loanapp.data.local.TokenDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenDataStoreRepository @Inject constructor(
    private val tokenDataSource: TokenDataSource
) {

    suspend fun saveToken(token: String) = tokenDataSource.saveToken(token)

    suspend fun removeToken() = tokenDataSource.removeToken()

    val savedToken: Flow<String> = tokenDataSource.savedToken
}