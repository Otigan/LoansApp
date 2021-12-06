package com.example.loanapp.domain.repository

import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.domain.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun login(loginRequestBody: LoginRequestBody): Flow<Resource<String>>
}