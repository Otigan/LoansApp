package com.example.loanapp.data.remote.model

data class LoginRequestBody(
    val name: String,
    val password: String
)

data class RegisterRequestBody(
    val name: String,
    val password: String
)