package com.example.loanapp.data.remote.data_source.auth

import com.example.loanapp.data.remote.api.LoansApi
import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.data.remote.model.UserDto
import javax.inject.Inject

class RegisterDataSourceImpl @Inject constructor(private val loansApi: LoansApi) :
    RegisterDataSource {

    override suspend fun register(registerRequestBody: RegisterRequestBody): UserDto =
        loansApi.register(registerRequestBody)

}