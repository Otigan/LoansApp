package com.example.loanapp.data.remote.data_source.auth

import com.example.loanapp.data.api.LoansApi
import com.example.loanapp.data.remote.model.LoginRequestBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(private val loansApi: LoansApi) : LoginDataSource {

    override suspend fun login(loginRequestBody: LoginRequestBody): Flow<Response<String>> =
        flow {
            val result = loansApi.login(loginRequestBody)
            emit(result)
        }
}