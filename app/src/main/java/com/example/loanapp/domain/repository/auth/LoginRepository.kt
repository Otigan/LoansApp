package com.example.loanapp.domain.repository.auth

import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun login(loginRequestBody: LoginRequestBody): Resource<String>
}