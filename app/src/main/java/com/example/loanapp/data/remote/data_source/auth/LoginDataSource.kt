package com.example.loanapp.data.remote.data_source.auth

import com.example.loanapp.data.remote.model.LoginRequestBody

interface LoginDataSource {

    suspend fun login(loginRequestBody: LoginRequestBody): String

}