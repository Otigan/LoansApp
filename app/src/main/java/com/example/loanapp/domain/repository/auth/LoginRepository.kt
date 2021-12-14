package com.example.loanapp.domain.repository.auth

import com.example.loanapp.data.remote.model.LoginRequestBody
import com.example.loanapp.data.remote.util.Resource

interface LoginRepository {

    suspend fun login(loginRequestBody: LoginRequestBody): Resource<String>
}