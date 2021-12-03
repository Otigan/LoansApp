package com.example.loanapp.domain.repository

import com.example.loanapp.domain.entity.User

interface RegisterRepository {

    suspend fun register(name: String, password: String): User

}