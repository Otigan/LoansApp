package com.example.loanapp.data.repository

import com.example.loanapp.data.LoginBody
import com.example.loanapp.data.api.LoansApi
import com.example.loanapp.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val api: LoansApi) : LoginRepository {

    override suspend fun login(name: String, password: String): String =
        api.login(LoginBody(name, password))

}