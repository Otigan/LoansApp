package com.example.loanapp.domain.repository.auth

import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.domain.entity.User
import com.example.loanapp.data.remote.util.Resource

interface RegisterRepository {

    suspend fun register(registerRequestBody: RegisterRequestBody): Resource<User>

}