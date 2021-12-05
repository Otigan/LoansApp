package com.example.loanapp.data.remote.data_source.auth

import com.example.loanapp.data.api.LoansApi
import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.data.remote.model.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RegisterDataSourceImpl @Inject constructor(private val loansApi: LoansApi) :
    RegisterDataSource {

    override suspend fun register(registerRequestBody: RegisterRequestBody): Flow<Response<UserDto>> =
        flow {
            val result = loansApi.register(registerRequestBody)
            emit(result)
        }
}