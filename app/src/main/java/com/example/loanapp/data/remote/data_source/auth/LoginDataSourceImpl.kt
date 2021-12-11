package com.example.loanapp.data.remote.data_source.auth

import com.example.loanapp.data.remote.api.LoansApi
import com.example.loanapp.data.remote.model.LoginRequestBody
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(private val loansApi: LoansApi) : LoginDataSource {

    override suspend fun login(loginRequestBody: LoginRequestBody): String =
        loansApi.login(loginRequestBody)

}