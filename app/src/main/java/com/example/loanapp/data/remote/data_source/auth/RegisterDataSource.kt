package com.example.loanapp.data.remote.data_source.auth

import com.example.loanapp.data.remote.model.RegisterRequestBody
import com.example.loanapp.data.remote.model.UserDto

interface RegisterDataSource {

    suspend fun register(registerRequestBody: RegisterRequestBody): UserDto

}