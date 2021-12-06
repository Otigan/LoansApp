package com.example.loanapp.data.remote.data_source.auth

import com.example.loanapp.data.remote.model.LoginRequestBody
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface LoginDataSource {

    suspend fun login(loginRequestBody: LoginRequestBody): Flow<Response<String>>

}