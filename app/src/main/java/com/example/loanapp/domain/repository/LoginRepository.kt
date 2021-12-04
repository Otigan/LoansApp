package com.example.loanapp.domain.repository

import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.domain.entity.AuthToken
import com.example.loanapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun login(loginRequestBody: LoginRequestBody): Flow<Resource<AuthToken>>
}