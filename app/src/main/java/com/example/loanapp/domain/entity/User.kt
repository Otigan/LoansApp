package com.example.loanapp.domain.entity

data class User(
    val user_id: Int = 0,
    val name: String? = null,
    val role: String? = null,
    val token: String? = null,
    val error: String? = null,
    val errorResponse: StateResponse? = null
)