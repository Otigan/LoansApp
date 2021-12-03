package com.example.loanapp.data.repository

import com.example.loanapp.data.LoginBody
import com.example.loanapp.data.api.LoansApi
import com.example.loanapp.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val api: LoansApi) : RegisterRepository {

    override suspend fun register(name: String, password: String) =
        api.register(LoginBody(name, password))

}