package com.example.loanapp.domain.repository

import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.domain.entity.User
import com.example.loanapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {

    suspend fun register(registerRequestBody: RegisterRequestBody): Flow<Resource<User>>

}