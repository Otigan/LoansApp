package com.example.loanapp.domain.repository

interface LoginRepository {

    suspend fun login(name: String, password: String): String
}