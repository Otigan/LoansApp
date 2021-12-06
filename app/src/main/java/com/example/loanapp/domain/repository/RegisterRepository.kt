package com.example.loanapp.domain.repository

import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.domain.entity.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RegisterRepository {

    suspend fun register(registerRequestBody: RegisterRequestBody): Flow<Response<User>>

}