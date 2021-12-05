package com.example.loanapp.data.remote.data_source.auth

import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.data.remote.model.UserDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

interface RegisterDataSource {

    suspend fun register(registerRequestBody: RegisterRequestBody): Flow<Response<UserDto>>

}